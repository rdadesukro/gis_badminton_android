package com.example.projek_kp_gis_badminton_2021.view;



import com.example.projek_kp_gis_badminton_2021.model.foto_slider.IsiItem_slider;
import com.example.projek_kp_gis_badminton_2021.model.komentar.IsiItem_komentar;

import java.util.List;


/**
 * This class represents the country view interface.
 *
 * @author Jean Carlos (Github: @jeancsanchez)
 * @date 09/03/18.
 * Jesus loves you.
 */
public interface komentar_view {

    void komentar(List<IsiItem_komentar> komentar);
    void status(String status);


}
