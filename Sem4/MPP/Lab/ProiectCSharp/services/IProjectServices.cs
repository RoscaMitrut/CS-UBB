using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using model;

namespace services
{
    public interface IProjectServices
    {
        void login(User user, IProjectObserver client);
        void logout(User user, IProjectObserver client);
        Excursie[] getExcursii(IProjectObserver client);
        Excursie[] getExcursiiLaLocSiOra(Excursie excursie,int oraMin,int oraMax,IProjectObserver client);
        void rezerva(Bilet bilet, IProjectObserver client);
    }
}
