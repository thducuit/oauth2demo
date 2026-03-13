curl "http://localhost:8080/dev/login?user=alice"

curl -X POST http://localhost:8081/api/documents \
-H "Content-Type: application/json" \
-d '{"title":"Doc1","content":"ACL"}'