using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ApiTest
{
    public interface Entity<ID>
    {
        void setId(ID id);
        ID getId();
    }

}
