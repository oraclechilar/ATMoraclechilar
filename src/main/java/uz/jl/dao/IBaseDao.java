package uz.jl.dao;

import java.util.List;

/**
 * Author : Qozoqboyev Ixtiyor
 * Time : 10.12.2021 20:35
 * Project : ATMoracle
 */
public interface IBaseDao <T>{
    void create(T obj);
    void delete(int index);
    List<T> list();
    void update(int index, T obj);
    void block(int index);
    void unBlock(int index);

}
