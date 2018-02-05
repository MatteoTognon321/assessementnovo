package com.example.matteotognon.assessement;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by MatteoTognon on 10/12/2017.
 */

public class AppResponse implements Serializable {
    @SerializedName("tarefa")
    private List<Informaapi> informacao;

    public List<Informaapi> getInformacao() {
        return informacao;
    }

    public void setInformacao(List<Informaapi> informacao) {
        this.informacao = informacao;
    }
}
