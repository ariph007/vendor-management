# Vendor Management

This vendor management consist of several entity or table. Table like below:
- Banks
- Categories
- Vendors
- VendorBanks
- Users
- Roles

The purpose of this project to create CRUD of vendor. One vendor have only one category. For example PT XYZ is a vendor with category is "Computers and Peripheral". This vendor has a bank account, and a vendor can have multiple bank account. This is to make user easier to send a Purchase Order if user want to purchase something related to Computers and Peripheral.
## Running development

To run this project, you need a docker for simply install service database and redis. You can see file "docker-compose.yml" in parent directory. You can run docker with

```bash
  docker compose up -d
```

After run docker, you can access PostgreSQL with database tool like dbeaver. And create a new database called `vendor`

In parent directory you will see directory called "database", you can run this sql query to create table and default data of roles and user.

Then you have postman directory in parent, this is a postman collection that you can import on your postman. You can create new Environment to automatically set token for each request.

Rate limiter based on role, in role table have column limit. we will read value of limit of role user that trying to make a request in filter chain. If it exceeded of limit, it will throw error 429 Too Many Requests.

Before create a request you must log in first, except for user registration


## Features

- Authentication with JWT
- Rate Limiter with Bucket4J and Redis
- Pagination for api get all
- Request Validation
- URI whitelist

## Features

- Authentication with JWT
- Rate Limiter with Bucket4J and Redis
- Pagination for api get all
- Request Validation
- URI whitelist


## API Reference

#### Login

```http
  POST /api/v1/login
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `username` | `string` | **Required**. Your username, If you run default data sql, you can use **satrio123** |
| `password` | `string` | **Required**. Your password, If you run default data sql, you can use **Admin123**|

