# Автотесты для сервиса карт доставки

[![Java CI with Gradle](https://github.com/alishass1/card-delivery-tests/actions/workflows/gradle.yml/badge.svg)](https://github.com/alishass1/card-delivery-tests/actions/workflows/gradle.yml)

## Описание

Автотесты для сервиса заказа карт доставки с использованием Selenide.

## Технологии

- Java 11
- JUnit 5
- Selenide
- Gradle
- GitHub Actions

## Запуск

1. Запустить SUT: `java -jar artifacts/app-card-delivery.jar`
2. Запустить тесты: `./gradlew test --info`

## Документация по дефектам

- [Issue #1](ссылка на ваш issue) - Невалидная дата проходит валидацию