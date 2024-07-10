using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace networking
{
    [Serializable]
    public class UserDTO
    {
        public String username {  get; set; }
        public String password { get; }
        public int id { get; set; }

        public UserDTO(String username, String password, int id) {
            this.username = username;
            this.password = password;
            this.id = id;
        }
    }
}
