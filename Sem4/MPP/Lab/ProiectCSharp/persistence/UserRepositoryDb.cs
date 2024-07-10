using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using log4net;
using model;
using utils;

namespace persistence
{
    public class UserRepositoryDb : IUserRepository
    {
        private static readonly ILog log = LogManager.GetLogger("UserRepositoryDb");

        IDictionary<String, String> props;
        public UserRepositoryDb(IDictionary<String, String> props)
        {
            log.Info("Creating UserRepositoryDb");
            this.props = props;
        }

        public User add(User elem)
        {

            var con = DBUtils.getConnection(props);
            log.Info("enter user add");
            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "insert into Useri (username, password) values (@username, @password)";

                var paramUsername = comm.CreateParameter();
                paramUsername.ParameterName = "@username";
                paramUsername.Value = elem.username;
                comm.Parameters.Add(paramUsername);

                var paramPassword = comm.CreateParameter();
                paramPassword.ParameterName = "@password";
                paramPassword.Value = elem.password;
                comm.Parameters.Add(paramPassword);

                var result = comm.ExecuteNonQuery();
                if (result == 0)
                {
                    log.Info("Not added");
                }
            }
            log.Info("exit user add");
            return elem;
        }

        public User update(int integer, User elem)
        {
            var con = DBUtils.getConnection(props);
            log.Info("enter user update");
            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "update Useri set username = @username, password = @password WHERE id = @id";
                var paramUsername = comm.CreateParameter();
                paramUsername.ParameterName = "@username";
                paramUsername.Value = elem.username;
                comm.Parameters.Add(paramUsername);

                var paramPassword = comm.CreateParameter();
                paramPassword.ParameterName = "@password";
                paramPassword.Value = elem.password;
                comm.Parameters.Add(paramPassword);

                var paramId = comm.CreateParameter();
                paramId.ParameterName = "@id";
                paramId.Value = integer;
                comm.Parameters.Add(paramId);


                var result = comm.ExecuteNonQuery();
                if (result == 0)
                {
                    log.Info("Not updated");
                }
            }
            log.Info("exit user update");
            return elem;
        }

        public IEnumerable<User> findAll()
        {
            IDbConnection con = DBUtils.getConnection(props);
            log.Info("enter user findall");
            IList<User> useri = new List<User>();
            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "select id,username, password from Useri";

                using (var dataR = comm.ExecuteReader())
                {
                    while (dataR.Read())
                    {
                        int id = dataR.GetInt32(0);
                        String username = dataR.GetString(1);
                        String password = dataR.GetString(2);
                        User user = new User();
                        user.password = password;
                        user.username = username;
                        user.setId(id);
                        useri.Add(user);
                    }
                }
            }
            log.Info("exit user findall");
            return useri;
        }

        public User findUser(string username, string password)
        {
            IDbConnection con = DBUtils.getConnection(props);
            log.Info("enter user finduser");
            User user = new User();
            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "select id,username,password from Useri WHERE username=@username AND password=@password";

                var paramUsername = comm.CreateParameter();
                paramUsername.ParameterName = "@username";
                paramUsername.Value = username;
                comm.Parameters.Add(paramUsername);

                var paramPassword = comm.CreateParameter();
                paramPassword.ParameterName = "@password";
                paramPassword.Value = password;
                comm.Parameters.Add(paramPassword);

                using (var dataR = comm.ExecuteReader())
                {
                    while (dataR.Read())
                    {
                        int id = dataR.GetInt32(0);
                        String usernameGasit = dataR.GetString(1);
                        String passwordGasit = dataR.GetString(2);
                        user.username = usernameGasit;
                        user.password = passwordGasit;
                        user.setId(id);
                    }
                }
            }
            log.Info("exit user finduser");
            return user;
        }
    }
}
