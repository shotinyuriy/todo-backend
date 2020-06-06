## unbind cf service
```bash
cf delete-service-key pivotal-postgresql my-service-key
cf unbind-service hello-world-rest-api pivotal-postgresql
cf delete-service pivotal-postgresql
```

```bash
cf create-service mlab sandbox todo-mongo
```

```bash
cf delete -r hello-world-rest-api
```