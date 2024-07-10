using Lab14.Domain;

namespace Lab14.Repository;

public interface IRepository<ID, E> where E : Entity<ID> { 
    /*
    E FindOne(ID id);
    E Save(E entity);
    E Delete(ID id);
    E Update(E entity);
    */
    IEnumerable<E> FindAll();
}