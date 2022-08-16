Task Book store.
  
  Through this web site, users can find the books they are looking for, buy them online, have information about themselves, orders and whether they have been delivered or not in their profiles. they can search by the genre, author and book name they want through the search section on the web page. Admin manages all running processes and monitors their status. Admin can edit all information related to the book and create new admin from users.


|       Activity      |	 Amin	   |  User  | 
| --- | --- | --- |
| LOGOUT/LOGIN        |     +    |   	+   |
| CRUD user           |     +    |   	+   |
| UPDATE_USER_STATUS  |     +    |   	-   |
| UPDATE_USER_STATUS	|     +    |   	-   |
| PASSWORD_UPDATE	    |     +    |   	-   |
| CRUD book	          |     +    |   	-   |
| ORDER_BOOK	        |     -    |   	+   |
| RATE_BOOK	          |     -    |   	+   |
| SEARCH_BOOK	        |     +    |   	+   |
| CRUD author of book |     +    |   	-   |
| SEARCH_AUTHOR       |     +    |   	-   |
| CRUD genre of book  |     +    |   	-   |
| SEARCH_GENRE        |     +    |   	+   |
| FIND_ALL_ORDERS	    |     +    |   	+   |
| CHANGE_ORDER_STATUS	|     +    |   	-   |
| SEARCH_ORDER	      |     +    |   	+   |
| CHANGE_LANGUAGE	    |     +    |   	+   |



Used design patterns:

    Observer
    Singleton
    Command
    MVC
    Proxy
    Factory Method
    Dao
  
Components used for the project:

    Java 15
    Maven
    Git
    JavaEE: Servlet, JSP, JSTL
    Server / Servlet container: Tomcat 9
    Database: PostgreSql
    JDBC
    HTML, CSS, JS, Bootstrap
    Logger: Log4J
    Tests: JUnit 4
