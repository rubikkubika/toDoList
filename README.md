# toDoList app

### 1. Что использовалось?
#### Backend:
+ Springboot
  > Rest controller
+ Mapstruct 
  > Для преобрразования Entity в DTO
+ Postgres
+ Spring data
  > Для DAO. Есть понимание как создавать и использовать общие дженерики, но у нас вроде простой проект.
#### Frontend:
+ Fullcallendar
+ Toastr
+ Tempus dominus
+ jQuery
+ Bootstrap
### 2. Эндпойнты

**/api/task/addnewtask** - PUT, создание новой задачи. Требует наличие в теле TaskDto

**/api/task/all** GET, получение списка всех задач из базы

 **/api/task/deletetask**  DELETE, удаление задачи. Требует в качестве праметра Long id   

 **/api/task/edittask** POST, редактирование задачи. Требует наличие в теле TaskDto  

 **/api/task/executetask** PATCH , измение статуса задачи на выполнено. Требует в качестве праметра Long id     

**/api/task/filtered** GET, получение списка задач, в зависимости от двух дат. Требует два параметра LocalDateTime startfilterdate,LocalDateTime endfilterdate  

 **/api/task/toptencompleted** GET, получение списка топ 10 выполненных задач. 

 **/api/task/{id}** GET, получение задачи по id.Требует в качестве праметра Long id 
### 3. Что понятно как делать, но не сделано так как время ограничено 
+ Аутентификация и авторизация Springboot
+ Подключение Swagger
+ Уведомления на email
+ Поиск
+ Переписать фронт на React