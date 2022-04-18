# Library API

## Docker run
* mvn clean package
* docker build -t docker-package-only-build-library:0.0.1-SNAPSHOT .
* docker run -d -p 8085:8085 docker-package-only-build-library:0.0.1-SNAPSHOT

## Пользователи

#### добавить пользователя
* http://localhost:8085/api/users/new/
* в теле запроса передать
* {"id": 3 }

#### получить список пользователей
* http://localhost:8085/api/users/all/

## Публикации

#### добавить публикацию
* http://localhost:8085/api/publications/new/
* в теле запроса передать
* {
  "name":"Anna Karenina",
  "description": "Story about Anna Karenina",
  "bookCount": 5
  }

#### получить публикацию по id
* http://localhost:8085/api/publications/?publicationId=1

#### все публикации постранично с сортировкой или без
* http://localhost:8085/api/publications/all/
* http://localhost:8085/api/publications/all/?page=0
* http://localhost:8085/api/publications/all/?sortBy=name
* http://localhost:8085/api/publications/all/?page=0&sortBy=name

#### полнотекстовый поиск по названию и описанию выдача отсортированна по ревелантности
* http://localhost:8085/api/publications/all/search/?searchText=Anna Karenina

## Работа с журналом выдачи публикаций

#### просмотреть постранично все записи в журнале
* http://localhost:8085/api/issuances/all/
* http://localhost:8085/api/issuances/all/?page=0

#### просмотреть все записи журнала по id пользователя
* http://localhost:8085/api/issuances/user/?userId=1
* http://localhost:8085/api/issuances/user/?page=0&userId=1

#### выдать издание пользователю
* http://localhost:8085/api/users/take-book/?userId=2&publicationId=4

#### принять издание от пользователя
* http://localhost:8085/api/users/return-book/?userId=2&publicationId=4