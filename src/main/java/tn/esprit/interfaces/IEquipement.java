package tn.esprit.interfaces;

import java.util.ArrayList;
import java.util.List;

public interface IEquipement<T> {
    void Add (T t);
    List<T> afficher();
    ArrayList<T> getAll();
    List<T> TriparName();
    List<T> TriparType();
    List<T> Rechreche(String recherche);
    void Update(T t);
    void DeleteByID(int id);
}
