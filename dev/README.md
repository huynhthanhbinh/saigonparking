<h3><b>README for developers of Saigon Parking Project</b></h3>
First written by: Huynh Thanh Binh (bht) in May 20th, 2020<br/>
Latest update by: Huynh Thanh BInh (bht) in June 22nd, 2020<br/>
<hr/>

<h2>Run SaigonParking environment</h2>
<h4>Some useful thing to work with saigonparking environment <br/></h4>

- Start docker compose:
    <br/>&emsp;&emsp; $ docker-compose up

- Stop docker compose:
    <br/>&emsp;&emsp; Ctrl + C
    
- Remove all containers:
    <br/>&emsp;&emsp; $ docker-compose rm
    
<h4>Another frequently used command</h4>
(Can be found in documents folder, file commands.txt)

<br/>
<h4>Note that : (For frontend team)</h4>

- please run docker-compose only in dev folder, and restore database as next step !
- please do not run docker-compose anywhere else in project's repository !
- if you have any problem or question, please ask backend team for help !


<h2>Run SaigonParking database</h2>
Restore one by one (.bak) file inside database folder, note that:<br/>
If it not automatically named the database which is about to create, please name them as followings:<br/>

- auth.bak --> AUTH
- user.bak --> USER
- parkinglot.bak --> PARKINGLOT 

<br/>
<h2>SaigonParking's architecture</h2>
![](../documents/architecture.png)