package Model;

import lombok.Data;
import static Controller.SimulatorConfig.*;

@Data
public class PandemicDisease {
    int incubationPeriod;
    int probAsymptomatic;
    int probFatality;
    int probInfection;
    int rehabAfterMinDays = 3;
    int rehabAfterMaxDays = 10;
}
