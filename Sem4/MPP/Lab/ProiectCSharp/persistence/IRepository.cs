using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace persistence
{
    public interface IRepository<ID, T>
    {

        T add(T elem);
        T update(ID id, T elem);
        IEnumerable<T> findAll();
    }
}