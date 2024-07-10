using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace model
{
    //[Serializable]
    //interface?
    public interface Entity<ID>
    {
        void setId(ID id);
        ID getId();
    }

}
