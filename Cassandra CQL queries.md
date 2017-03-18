## Cassandra - CQL queries

``` 
>> select cluster_name, listen_address from system.local;
```

```
>> create keyspace dev
... with replication = {'class':'SimpleStrategy','replication_factor':1};
```

```
>> use dev;
```

```
>> show host;
Connected to Test Cluster at 127.0.0.1:9042.
```

```
>> show version;
[cqlsh 5.0.1 | Cassandra 3.9.0 | CQL spec 3.4.2 | Native protocol v4]
```
```
>> create table emp (empid int primary key,
... emp_first varchar, emp_last varchar, emp_dept varchar);
```

```
>> insert into emp (empid, emp_first, emp_last, emp_dept)
... values (1,'fred','smith','eng');
```

```
>> update emp set emp_dept = 'fin' where empid = 1;
```

```
>> select * from emp;
>> select * from emp where empid = 1;
```

```
>> select * from emp where emp_dept = 'fin'; >>>>>>>>>>>>>>>>>>>>>>THIS DOESN'T WORK
So,
>> create index idx_dept on emp(emp_dept);
```

```
>> select * from emp where emp_dept = 'fin';
```

```
>> describe keyspaces;
myfirstcassandradb  system_auth  dev                 system_traces
system_schema       system       system_distributed
```

```
>> drop keyspace myfirstcassandradb;
```
