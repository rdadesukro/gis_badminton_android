package com.example.projek_kp_gis_badminton_2021.view;


import com.example.projek_kp_gis_badminton_2021.model.jenis.IsiItem_jenis;
import com.example.projek_kp_gis_badminton_2021.model.lapangan.IsiItem_lapangan;

import java.util.List;


/**
 * This class represents the country view interface.
 *
 * @author Jean Carlos (Github: @jeancsanchez)
 * @date 09/03/18.
 * Jesus loves you.
 */
public interface jenis_view {

    void jenis(List<IsiItem_jenis> jenis);
    void status(String status,String pesan);


}
