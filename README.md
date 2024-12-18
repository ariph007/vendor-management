
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






## Features

- Authentication with JWT
- Rate Limiter with Bucket4J and Redis
- Pagination for api get all
- Request Validation
- URI whitelist