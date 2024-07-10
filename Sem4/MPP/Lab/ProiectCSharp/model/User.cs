using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using utils;

namespace model
{
    public class User : Entity<int>
    {
        public String username { get; set; }
        public String password { get; set; }

        public int id;

        public User(string username, string password)
        {
            this.username = username;
            this.password = Crypt.Encrypt(password);
        }

        public User()
        {
            this.username = null;
            this.password = null;
        }

        public void setId(int id)
        {
            this.id = id;
        }

        public int getId()
        {
            return this.id;
        }
    }
}
