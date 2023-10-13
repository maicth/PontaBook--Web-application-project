/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

/**
 *
 * @author DELL
 */
public class ControlPaging {
    private int size;
    private int cp;
    private int nrpp;
    private int begin;
    private int end;
    private int np;
    
    private int pageStart;
    private int pageEnd;

    public ControlPaging() {
    }

    public ControlPaging(int nrpp, int cp, int size) {
        this.size = size;
        this.cp = cp;
        this.nrpp = nrpp;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getCp() {
        return cp;
    }

    public void setCp(int cp) {
        this.cp = cp;
    }

    public int getNrpp() {
        return nrpp;
    }

    public void setNrpp(int nrpp) {
        this.nrpp = nrpp;
    }

    public int getBegin() {
        return begin;
    }

    public void setBegin(int begin) {
        this.begin = begin;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public int getPageStart() {
        return pageStart;
    }

    public void setPageStart(int pageStart) {
        this.pageStart = pageStart;
    }

    public int getPageEnd() {
        return pageEnd;
    }

    public void setPageEnd(int pageEnd) {
        this.pageEnd = pageEnd;
    }

    public int getNp() {
        return np;
    }

    public void setNp(int np) {
        this.np = np;
    }
    
    public void calc (){
        np = (nrpp + size - 1)/ nrpp;
        
        cp = cp < 0 ? 0: cp;
        cp = cp > np - 1? np - 1: cp;
        
        begin = nrpp * cp;
        end = begin + nrpp - 1;
        
        pageStart = cp - 1;
        pageStart = pageStart < 0? 0: pageStart;
        pageEnd = cp + 1;
        pageEnd = pageEnd > np - 1? np - 1: pageEnd;
    }
}
