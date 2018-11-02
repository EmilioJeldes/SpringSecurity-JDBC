# Spring Security MVC
Spring security implementation example using mvc and `inMemoryAuthentication`


`````java
@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/", "/public").permitAll()
                .antMatchers("/private", "/user/**").hasAnyRole("USER")
                .antMatchers("/admin").hasAnyRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin().permitAll()
                .and()
                .logout().permitAll();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

        User.UserBuilder users = User.builder().passwordEncoder(encoder::encode);

        auth.inMemoryAuthentication()
                .withUser(users.username("admin").password("12345").roles("ADMIN", "USER"))
                .withUser(users.username("user").password("12345").roles("USER"));
    }
}
`````
1. SpringSecurityConfig extends `WebSecurityConfigurerAdapter` which contains all the methods to implement security

2. `@Configuration` and `@EnableConfiguration` to register with spring boot

3. `configure(HttpSecurity http)` to set configuration

4. Register an AuthenticationManager and pass the users in memory

## Implement custom Login page
####1. In the SecurityConfiguration class
````java
http
    ...
    
    .formLogin().loginPage("/login").permitAll()
    
    ...
````

####2. Create LoginController class to register the get method to redirect to the new login view
````java
@RestController
@RequestMapping('/login')
public class LoginController {
    
    @GetMapping("/login")
    public String login(Model model, Principal principal) {
        if (principal != null){
            return "redirect:/";
        }
        
        if (error != null) {
            model.addAtribute("error", "Usuario y contraseña incorrectas");
        }
        
        return "login";
    }
}
````
* returns the view login.html or redirects to the index page in case there's already a user logged in.

####3. Create view login into resources/templates using thymeleaf and bootstrap 4
```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head th:replace="layout/layout :: head"></head>
<body>

<header th:replace="layout/layout :: header"></header>

<div class="container py-4">

    <div class="card border-primary text-center">
        <div class="card-header">Por favor Sign In!</div>
        <div class="card-body">

            <form th:action="@{/login}" method="post">

                <div class="form-group col-sm-6">
                    <input type="text" name="username" id="username"
                        class="form-control" placeholder="UserName" autofocus required />
                </div>

                <div class="form-group col-sm-6">
                    <input type="password" name="password" id="password"
                        class="form-control" placeholder="Password" required />
                </div>

                <div class="form-group col-sm-6">
                    <input type="submit" class="btn btn-lg btn-primary btn-block"
                        value="Sign In" />
                </div>
                <input type="hidden" th:name="${_csrf.parameterName}" th:value="${_csrf.token}"/>

            </form>

        </div>

    </div>

</div>

<footer th:replace="layout/layout :: footer"></footer>
</body>
</html>
```

##Implement Custom 403 error page
####1. Register the view controller
````java
@Configuration
public class MvcConfig implements WebMvcConfigurer {
	
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/error_403").setViewName("error_403");
	}

	
}
````

####2. Add the configuration in the SecurityConfig class
````java
http
    ...
    
    .exceptionHandling().accessDeniedPage("/error_403");
````

####3. Create the view error_403.html in resources/templates

````html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head th:replace="layout/layout :: head"></head>
<body>

	<header th:replace="layout/layout :: header"></header>

	<div class="container py-4">

		<div class="card bg-danger text-white">
			<div class="card-header">Error: Acceso Denegado!</div>
			<div class="card-body">
				<h5 class="card-title">
					Los sentimos <span th:text="${#authentication.name}"></span>,
					NO tienes permisos para acceder a este recurso.
				</h5>
				<a th:href="@{/listar}" class="btn btn-outline-light" role="button">Ir
					al inicio</a>

			</div>
		</div>
	</div>

	<footer th:replace="layout/layout :: footer"></footer>
</body>
</html>
````

##EnableGlobalMethodSecurity
To secure service or controller methods with `Secure` or `PreAuthorize`
````java
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
    
    ...
    
}
````