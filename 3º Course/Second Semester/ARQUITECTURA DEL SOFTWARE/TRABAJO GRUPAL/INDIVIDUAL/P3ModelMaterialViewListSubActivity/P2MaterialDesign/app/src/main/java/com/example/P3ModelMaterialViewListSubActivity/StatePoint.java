package com.example.P3ModelMaterialViewListSubActivity;

import com.parse.ParseClassName;
import com.parse.ParseObject;

@ParseClassName("StatePoint")
public class StatePoint extends ParseObject {
    public StatePoint() {
        super();
    }
    public StatePoint(String idUser, String trainingProgram, double velocity, double slope) {
        super();
        setId(idUser);
        setTrainingProgram(trainingProgram);
        setVelocity(velocity);
        setSlope(slope);
    }

    public String getId() {
        return getString("idUser");
    }

    public void setId(String idUser) {
        put("idUser", idUser);
    }

    public String getTrainingProgram() {
        return getString("trainingProgram");
    }

    public void setTrainingProgram(String trainingProgram) { put("trainingProgram", trainingProgram);}

    public double getVelocity() {
        return getDouble("velocity");
    }

    public void setVelocity(double velocity) {
        put("velocity", velocity);
    }

    public double getSlope() {
        return getDouble("slope");
    }

    public void setSlope(double slope) {
        put("slope", slope);
    }

    public String toString() {
        return "StatePoint{" +
                "idUser='" + getId() + '\'' +
                ", trainingProgram='" + getTrainingProgram() + '\'' +
                ", velocity=" + getVelocity() +
                ", slope=" + getSlope() +
                '}';
    }
}
