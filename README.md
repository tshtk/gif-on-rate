[Тестовое задание](TASK.md)

## Инструкция по запуску

Запуск приложения осуществляется любым из указанных способов

### Gradle

`gradle bootRun` - для запуска приложения

### Docker

`docker build -t gif-on-rate .` - для локальной сборки Docker образа

`docker run -p 8080:8080 gif-on-rate` - для запуска приложения в Docker контейнере

### Docker Compose

`docker-compose up` - для загрузки образа с Docker Hub и запуска приложения в Docker контейнере

`docker-compose -f docker-compose-ci.yml up` - для локальной сборки Docker образа и запуска приложения в Docker контейнере

## API приложения

Запрос:
`GET /gifs`

c параметром запроса:
`currency=CODE`,\
где CODE - трехзначный буквенный код валюты (ISO 4217),

вернет gif, иллюстрирующую изменение со вчерашнего дня курса указанной валюты к USD.

Пример запроса:\
`GET /gifs?currency=RUB`
