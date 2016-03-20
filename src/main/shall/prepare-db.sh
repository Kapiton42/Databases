createdb test-db
createuser -P test-user
psql -h localhost test-db test-user < ../resources/create-user-tables.sql
psql -h localhost test-db test-user < ../resources/create-realestate-tables.sql