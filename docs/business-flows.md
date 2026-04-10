# EDU-PATH Business Flow Diagrams

## 1. Tra_cú theo trình d_ (Level Filter)

```plantuml
@startuml
participant "H_c viên (Browser)" as User
participant "CourseController" as Ctrl
participant "CourseService" as Svc
participant "CourseRepository" as Repo
participant "CourseList (Thymeleaf)" as View

User -> View : Ch_n Level 'Intermediate'
View -> Ctrl : GET /course/list?level=Intermediate
Ctrl -> Svc : filterCourses("Intermediate", null)
Svc -> Repo : findByLevelAndMaxFee("Intermediate", null)
Repo --> Svc : List<Course>
Svc --> Ctrl : List<Course>
Ctrl -> View : model.addAttribute("courses", list)
View --> User : Hi_n th_ các khóa Intermediate trong Layout
@enduml
```

## 2. Xem chi ti_t khóa hoç (Course Detail)

```plantuml
@startuml
participant "H_c viên (Browser)" as User
participant "CourseController" as Ctrl
participant "CourseService" as Svc
participant "CourseRepository" as Repo
participant "CourseDetail (Thymeleaf)" as View

User -> User : Click "Chi ti_t" button
User -> Ctrl : GET /course/detail/IELTS-6.5
Ctrl -> Svc : getCourseByCode("IELTS-6.5")
Svc -> Repo : findByCourseCode("IELTS-6.5")
Repo --> Svc : Optional<Course>
Svc --> Ctrl : Optional<Course>
Ctrl -> View : model.addAttribute("course", course)
View -> View : Check isFull status
View --> User : Hi_n th_ chi ti_t khóa hoç
@enduml
```

## 3. C_p nh_t thông tin khóa hoç (Admin Update)

```plantuml
@startuml
participant "Admin (Browser)" as Admin
participant "CourseController" as Ctrl
participant "CourseService" as Svc
participant "CourseRepository" as Repo
participant "AdminPage (Thymeleaf)" as View

Admin -> Admin : Click "Ch_nh s_a" button
Admin -> Ctrl : GET /course/edit/1
Ctrl -> Svc : getCourseById("1")
Svc -> Repo : findById("1")
Repo --> Svc : Optional<Course>
Svc --> Ctrl : Optional<Course>
Ctrl -> View : Return edit form
View --> Admin : Display edit form

Admin -> View : Edit fee and start date
Admin -> Ctrl : POST /course/edit (form data)
Ctrl -> Svc : saveCourse(course)
Svc -> Repo : save(course)
Repo --> Svc : Course
Svc --> Ctrl : Course
Ctrl -> Admin : redirect:/course/admin (PRG Pattern)
@enduml
```

## 4. Hûy khóa hoç (Delete Course)

```plantuml
@startuml
participant "Admin (Browser)" as Admin
participant "CourseController" as Ctrl
participant "CourseService" as Svc
participant "CourseRepository" as Repo
participant "AdminPage (Thymeleaf)" as View

Admin -> Admin : Click "Hûy khóa hoç" button
Admin -> Admin : Confirm deletion dialog
Admin -> Ctrl : POST /course/delete/1
Ctrl -> Svc : canDeleteCourse("1")
Svc -> Repo : findById("1")
Repo --> Svc : Optional<Course>
Svc --> Ctrl : boolean (studentCount == 0?)

alt Student Count = 0
    Ctrl -> Svc : deleteCourse("1")
    Svc -> Repo : deleteById("1")
    Repo --> Svc : boolean
    Svc --> Ctrl : boolean
    Ctrl -> Admin : redirect:/course/admin with success message
else Student Count > 0
    Ctrl -> Admin : redirect:/course/admin with error message
end
@enduml
```

## 5. L_c khóa hoç theo h_c phí (Fee Filter)

```plantuml
@startuml
participant "H_c viên (Browser)" as User
participant "CourseController" as Ctrl
participant "CourseService" as Svc
participant "CourseRepository" as Repo
participant "CourseList (Thymeleaf)" as View

User -> View : Nh_p h_c phí t_i da: 5,000,000
User -> Ctrl : GET /course/list?maxFee=5000000
Ctrl -> Svc : filterCourses(null, 5000000.0)
Svc -> Repo : findByLevelAndMaxFee(null, 5000000.0)
Repo --> Svc : List<Course> (fee <= 5M)
Svc --> Ctrl : List<Course>
Ctrl -> View : model.addAttribute("courses", list)
View -> View : Format fee with #numbers.formatDecimal
View --> User : Hi_n th_ các khóa phí <= 5M
@enduml
```

## 6. Ang ký t_ v_n (Registration Flow)

```plantuml
@startuml
participant "H_c viên (Browser)" as User
participant "CourseController" as Ctrl
participant "RegisterPage (Thymeleaf)" as View

User -> View : Click "Ang ký t_ v_n"
View -> Ctrl : GET /course/register
Ctrl -> View : Return registration form
View --> User : Display registration form

User -> View : Fill registration form
User -> View : Submit form
User -> Ctrl : POST /course/register (form data)
Ctrl -> Ctrl : Process registration logic
Ctrl -> User : Display confirmation message
@enduml
```

## 7. Lu_ng d_ li_u h_th_ng (System Data Flow)

```plantuml
@startuml
participant "Database (Mock Data)" as DB
participant "CourseRepository" as Repo
participant "CourseService" as Svc
participant "CourseController" as Ctrl
participant "Thymeleaf Views" as View
participant "Browser" as User

DB -> Repo : Initialize 7 mock courses
Repo -> Svc : Provide data access methods
Svc -> Ctrl : Business logic operations
Ctrl -> View : Pass model data
View -> User : Render HTML pages

User -> View : HTTP requests
View -> Ctrl : Request mapping
Ctrl -> Svc : Service calls
Svc -> Repo : Data operations
Repo -> DB : Data access
@enduml
```
