<h2>Solution to back up database to develop saigonparking project - @author: bht</h2>
<br/>1st solution: restore all backup files under backup folder.
<br/>2nd solution: restore by execute all generated scripts under script folder.
<hr>
Note that: for 2nd solution please execute scripts like the following order: (4 steps)
<br/><br/><h6>Step 1: DROP IF EXISTS AND CREATE DATABASE</h6>Execute all scripts under extra folder
<br/><br/><h6>Step 2: CREATE ALL TABLE AND FUNCTIONS</h6>Execute all scripts under schema folder
<br/><br/><h6>Step 3: RESTORE DATA OF ALL TABLES</h6>Execute all scripts under data folder 
<br/><br/><h6>Step 4: RESTORE TRIGGERS OF ALL TABLES</h6>Execute all scripts under trigger folder