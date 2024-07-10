using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using model;

namespace persistence
{
    public interface IUserRepository : IRepository<int, User>
    {
        User findUser(String username, String password);
    }
}
