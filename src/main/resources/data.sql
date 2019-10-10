insert into user (id, password, firstname, lastname, email) values
  ('charlie', 'test', 'Charlie', 'User', 'charlie-the-user@test.com'),
  ('alice', 'test', 'Alice', 'Admin', 'alice-the-admin@test.com'),
  ('joe', 'test', 'Joe', 'Guest', 'joe-the-guest@test.com');
  
insert into groups (id, name) values
  ('admins', 'Administrators'),
  ('users', 'Users'),
  ('guests', 'Guests'); 
  
insert into group_authorities (group_id, authority) values
  ('admins', 'read_data'),
  ('admins', 'change_data'),
  ('admins', 'delete_data'),
  ('users', 'read_data'),
  ('users', 'change_data'),
  ('guests', 'read_data');

insert into group_members (user_id, group_id) values
  ('alice', 'admins'),
  ('charlie', 'users'),
  ('joe', 'guests');
 