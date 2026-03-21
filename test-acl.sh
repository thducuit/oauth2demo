curl "http://localhost:8081/dev/login?user=alice"

curl -X POST http://localhost:8081/api/documents \
-H "Content-Type: application/json" \
-d '{"title":"Doc1","content":"ACL"}'

curl -H "X-USER: alice" \
-X POST "http://localhost:8081/api/documents/1/grant?user=bob&permission=READ"

curl -H "X-USER: bob" \
http://localhost:8081/api/documents/1