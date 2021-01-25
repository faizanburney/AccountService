package com.fburney.task.model;

import lombok.Data;

import java.util.List;


@Data
public class StockResponse {

    public List<Integer> t = null;
    public List<Double> c = null;
    public List<Double> o = null;
    public List<Double> h = null;
    public List<Double> l = null;
    public List<Integer> v = null;
    public List<Integer> vo = null;
    public String s;
}