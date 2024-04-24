package tn.esprit.interfaces;

import java.util.List;

public interface IEvenement<T>{
    void Add (T t);
    List<T> afficher();
    List<T> TriparName();
    List<T> TriparType();
    List<T> Rechreche(String recherche);
    void Update(T t);
    void DeleteByID(int id);
}
