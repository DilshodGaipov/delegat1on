# DELEGAT1ON — Delegate Manager for Jira (DC 8.13.1) — v1.0.8

## Новое в 1.0.8
- Категории и Права: хранение в PluginSettings, UI и REST.
- Workflow учитывает права (assignee/reportер, post-function).
- JQL принимает опциональный 2‑й аргумент — категория.

## Сборка
```bash
mvn -DskipTests package
```
JAR: `target/delegat1on-1.0.8-ddcorp.jar`

## Страницы
- `/plugins/servlet/delegat1on/getstarted`
- `/plugins/servlet/delegat1on/category`
- `/plugins/servlet/delegat1on/permissions`
- `/plugins/servlet/delegat1on/configforadmin`
- `/plugins/servlet/delegat1on/configforuser`
- `/plugins/servlet/delegat1on/docs`

## REST
- `GET /rest/delegat1on/1.0/ping`
- `GET/POST/PUT/DELETE /rest/delegat1on/1.0/records`
- `GET/POST/DELETE /rest/delegat1on/1.0/categories`
- `GET/PUT /rest/delegat1on/1.0/perms`

## Workflow
- **Condition** `User is Delegate of` — проверяет активного делегата (поле: `field=assignee|reporter`).
- **Post-function** `Assign to Delegate of` — назначает на делегата, учитывая право `postFunctionAssign`.

## JQL
- `assignee in delegatesOf(currentUser())`
- `assignee in delegatesOf(currentUser(), "Vacation")`
- `reporter in delegatorsOf("alice")`
