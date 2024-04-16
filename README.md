# postItBloggingApp

Requirement: Developed a secure blogging application including:
1) User Registration and sign in feature.
2) Publish blogs/articles and images.
3) Allow comments on blogs/articles.
4) Categorise posts based on tags.

> [!NOTE]
> Prerequisites:
> -  Understanding of core Java (OOPs, exceptions, Stream APIs, Lambda expressions)
> -  Basics of Spring Framework, SPring Boot, MySQL Database
> -  Working knowledge of STS IDE
> -  Working knowledge of AWS cloud
> -  Understanding about HTTP Response codes:
>     -   	200 OK - Request is successful
>     -   	201 Created - Request is successful and new resource is created
>     -   	400 Bad Request - Invalid request made to server
>     -   	401 Unauthorized - Authentication is required for resource
>     -   	404 Not Found - Resource not found
>     -   	500 Internal Server Error - Error occured on server and request can not be fulfilled


Backend:

Built realtime REST APIs using SpringBoot, Spring Security, JWT, Spring Data (JPA) Hibernate, MySQL

* Language: Java 8+
* IDE: STS
* Server: Apache Tomcat (embeded)
* Cloud: AWS (Elastic Beanstalk, Amazon RDS, Route 53, Amazon S3)
* Database: MySQL
* Documentation: Swagger(OpenAPI v1.8)
* Postman Rest Client


Key skills gained:
* Adding profiles for different environment
* Deploying Spring Boot in production on AWS cloud (using BeanStalk service)
* Implement Input validation, Pagination and Sorting, Custom Exceptions
* Managing Resource and Sub-Resource
> ex: http://localhoast:8282/Maths/10/Trignometry/3
>  	
>   - 	Maths- Resource
>   - 	10- Resource ID
>   - 	Trignometry- Sub-Resource
>   - 	3- Sub-Resource ID

Implementations: 
* Creating endpoints
* Managing complex DB structure
* Role based authentication and JWT security configurations
* Handling Exceptions and defining custom exceptions
* Using Data Transfer Objects (DTO) for data Transfer
* Document API endpoint development using Swagger 3 (Spring fox)


Frontend:

Resource links:
* Swagger (OpenAPI)- [https://swagger.io/](https://springdoc.org/)
* Spring- https://docs.spring.io/
* Spring Security- https://docs.spring.io/spring-security/reference/
* Amazon Cloud Services- https://docs.aws.amazon.com/
* React- https://legacy.reactjs.org/docs/create-a-new-react-app.html
