using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using model;

namespace networking
{
    public class DTOUtils
    {
        public static UserDTO getDTO(User user)
        {
            return new UserDTO(user.username, user.password, user.id);
        }
        public static User fromDTO(UserDTO userdto) 
        {
            User usr = new User();
            usr.id = userdto.id;
            usr.password = userdto.password;
            usr.username = userdto.username;
            return usr;
        }

        public static BiletDTO getDTO(Bilet bilet)
        {
            return new BiletDTO(bilet);
        }

        public static Bilet fromDTO(BiletDTO biletdto)
        {
            int id = biletdto.id;
            String numeClient = biletdto.numeClient;
            int numarTelefonClient = biletdto.numarTelefonClient;
            int idExcursie = biletdto.idExcursie;
            int numarPersoane = biletdto.numarPersoane;
            Bilet bilet = new Bilet(numarPersoane,numeClient,numarTelefonClient,idExcursie);
            bilet.setId(id);
            return bilet;
        }
        public static ExcursieDTO getDTO(Excursie excursie)
        {
            return new ExcursieDTO(excursie);
        }
        public static Excursie fromDTO(ExcursieDTO excursiedto)
        {
            int id = excursiedto.id;
            String obiectiv = excursiedto.obiectivVizitat;
            DateTime ora = DateTime.Parse(excursiedto.oraPlecare);
            String firma = excursiedto.firmaTransport;
            Double pret = excursiedto.pret;
            int locuri =excursiedto.locuriDisponibile;

            Excursie excursie = new Excursie(obiectiv,ora,firma,pret,locuri);
            excursie.setId(id);
            return excursie;
        }
        public static ExcursieDTO[] getDTO(Excursie[] excursii)
        {
            ExcursieDTO[] excursiiDto = new ExcursieDTO[excursii.Length];
            for (int i = 0;i< excursiiDto.Length;i++)
            {
                excursiiDto[i] = getDTO(excursii[i]);
            }
            return excursiiDto;
        }
        public static Excursie[] fromDTO(ExcursieDTO[] excursiidto)
        {
            Excursie[] excursii = new Excursie[excursiidto.Length];
            for(int i = 0; i< excursiidto.Length; i++)
            {
                excursii[i] = fromDTO(excursiidto[i]);
            }
            return excursii;
        }
    }
}
