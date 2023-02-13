package com.example.waterdrink.Model;

public class Calcular {

    private double mlJovem = 40.0;
    private double mlAdulto = 35.0;
    private double mlIdoso = 30.0;
    private double mlVelho = 25.0;

    private double resultadoMl = 0.0 ;
    private double totalMl = 0.0;

    public Calcular() {


    }

    public void calcularTotalMl(Double peso , int idade ) {

        if (idade <= 17) {

            resultadoMl = peso * mlJovem;
            totalMl = resultadoMl;

        } else if (idade <= 55) {
            resultadoMl = peso * mlAdulto;
            totalMl = resultadoMl;

        } else if (idade <= 65) {
            resultadoMl = peso * mlIdoso;
            totalMl = resultadoMl;

        } else {
            resultadoMl = peso * mlVelho;
            totalMl = resultadoMl;

        }

    }

    public Double ResultadoML(){



        return totalMl;
    }

}
