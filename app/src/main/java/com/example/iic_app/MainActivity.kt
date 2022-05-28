package com.example.iic_app

import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.*
import android.widget.AdapterView.OnItemSelectedListener
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity(), OnItemSelectedListener {


    var grain_no=0
    var prop_choice=0
    var select_prop =""
    var grain_length=0


    var propellants = arrayOf<String?>("Potassium Nitrate(KN)- 70%  Sucrose(SU)-30%",
        "Potassium Nitrate(KN)- 65%  Sorbitol(SB)-10% Sucrose(SU)- 24% Carbon-1%",
        "Potassium Nitrate(KN)- 65% Sucrose-35%",
        "Ammonium Perchlorate(AP)-70% Polybutadiene Acrylonitrile(PBAN)-15% Iron(III) Oxide-3% Al Powder(metal fuel)-12% Additives-<=3%",
        "Ammonium Perchlorate(AP)-75% Polybutadiene Acrylonitrile(PBAN)-15% Iron(III) Oxide-3% Al Powder(metal fuel)-7% Additives-<=3%",
        "Ammonium Nitrate(AN)-70% Glycidyl Azide Prepolymer(GAP)-15% Al Powder-10% Zinc Chloride-3% Iron(III) Oxide 2%")

    var core_type = arrayOf<String?>("round","star")

    var percentage = arrayOf<String?>("70","75","80")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var prop: Int?=0
        var length : Double? =0.0
        var coreType=""
        var dm: Double=0.0
        var dc: Double=0.0



        val spin = findViewById<Spinner>(R.id.prop_spinner)
        spin.onItemSelectedListener = this

        val core_spin = findViewById<Spinner>(R.id.core_select)
        core_spin.onItemSelectedListener = this

        val motor_percentage_spin = findViewById<Spinner>(R.id.prop_percent)
        motor_percentage_spin.onItemSelectedListener = this


        val ad: ArrayAdapter<*> = ArrayAdapter<Any?>(
            this,
            R.layout.spinner_design,
            propellants)

        ad.setDropDownViewResource(
            android.R.layout.simple_spinner_dropdown_item)


        val cd: ArrayAdapter<*> = ArrayAdapter<Any?>(
            this,
            R.layout.spinner_design,
            core_type)

        cd.setDropDownViewResource(
            android.R.layout.simple_spinner_dropdown_item)


        val ab: ArrayAdapter<*> = ArrayAdapter<Any?>(
            this,
            R.layout.spinner_design,
            percentage)

        ab.setDropDownViewResource(
            android.R.layout.simple_spinner_dropdown_item)


        spin.adapter = ad
        motor_percentage_spin.adapter=ab
        core_spin.adapter=cd

        val per = findViewById<View>(R.id.editTextDate) as EditText
        val len = findViewById<View>(R.id.editTextDate2) as EditText
        val per_in =findViewById<View>(R.id.editTextDate3) as EditText
        per.setText("0")
        len.setText("0")
        per_in.setText("0")




        //generating the output
        val popUp=PopupWindow(this)
        val view=layoutInflater.inflate(R.layout.another_view,null)
        val button : ImageView=findViewById(R.id.res)
        button.setOnClickListener(View.OnClickListener(){

            length=(per.getText().toString()).toDoubleOrNull()
            select_prop= spin.getSelectedItem().toString()
            prop= (motor_percentage_spin.getSelectedItem().toString()).toIntOrNull()
            coreType=core_spin.getSelectedItem().toString()
            dm=(len.getText().toString()).toDouble()
            dc=(per_in.getText().toString()).toDouble()

            calculate_grains(view,length,select_prop,prop,coreType,dm,dc)
            popUp.contentView=view
            popUp.showAtLocation(view, 1,0,0)
            val close=view.findViewById<Button>(R.id.close)
            close.setOnClickListener(){
                popUp.dismiss()
            }
        }
        );


    }

    override fun onItemSelected(parent: AdapterView<*>?,
                                view: View, position: Int,
                                id: Long) {
    prop_choice=position

    }

    override fun onNothingSelected(parent: AdapterView<*>?) {}

    fun calculate_grains(view: View,length : Double?,select_prop : String , prop : Int? , coreType : String , dm :Double,dc : Double)
    {
        var str=" "
        //val info_display :EditText
        var total_mass : Double =0.0
        grain_length= ((prop!! * length!!)/100 as Int).toInt()
        var cn = (grain_length/6)-1 as Int

        str="TOTAL NUMBER OF GRAINS:"+(cn+2)+"\n"+cn+" GRAINS OF 6CM WITH CORE \n 1 GRAIN OF 3 CM WITHOUT CORE \n 1 GRAIN OF 3 CM WITH CORE"
        if (coreType=="star")
        {
            if (grain_length % 6 == 0)
            {
                if(prop_choice==0)
                {total_mass=1.8*(cn*6*2.0189*0.25*dm*dm + 3.1415*3*0.25*dm*dm + 3*2.0189*0.25*dm*dm)
                str+="\n\nTOTAL MASS OF PROPELLANT TO BE USED:"+total_mass+" grams\n Propellant Formulation: Potassium Nitrate(KN)- 70%  Sucrose(SU)-30%\nMASS OF EACH GRAIN BEFORE CASTING:\n Of 6cm standard with core: "+1.8*(2.0189*6*0.25*dm*dm)+" grams\n Of 3cm standard without core: "+1.8*(3.1415*3*0.25*dm*dm)+"grams\n Of 3cm with core: "+1.8*(2.0189*3*0.25*dm*dm)+"grams \nMASS OF Potassium Nitrate(KN): "+0.7*total_mass+" grams \nMASS OF Sucrose(SU):"+0.3*total_mass+"grams \nBURN RATE: 3.96-4.00 mm/sec \nTHRUST: 600-620 kN \nSPECIFIC IMPULSE: 150-155 sec"}
                else
                if(prop_choice==1)
                {total_mass=1.83*(cn*6*2.0189*0.25*dm*dm + 3.1415*3*0.25*dm*dm + 3*2.0189*0.25*dm*dm)
                str+="\n\nTOTAL MASS OF PROPELLANT TO BE USED: "+total_mass+" grams\nPropellant Formulation: Potassium Nitrate(KN)- 65%  Sorbitol(SB)-10% Sucrose(SU)- 24% Carbon-1%\nMASS OF EACH GRAIN BEFORE CASTING:\n Of 6cm standard with core: "+1.83*(2.0189*6*0.25*dm*dm)+"grams\n Of 3cm standard without core: "+1.83*(3.1415*3*0.25*dm*dm)+"grams\n Of 3cm with core: "+1.83*(2.0189*3*0.25*dm*dm)+"grams\nMASS OF Potassium Nitrate(KN): "+0.65*total_mass+"grams\nMASS OF Sorbitol(SB):"+0.1*total_mass+"grams\nMASS OF Sucrose(SU): "+0.24*total_mass+"grams\nMASS OF Carbon: "+0.01*total_mass+"grams\nBURN RATE: 1.70-2.00 mm/sec\nTHRUST: 680-720 kN\nSPECIFIC IMPULSE: 160-165 sec"}
                else if(prop_choice==2)
                {total_mass=1.8*(cn*6*2.0189*0.25*dm*dm + 3.1415*3*0.25*dm*dm + 3*2.0189*0.25*dm*dm)
                str+="\n\nTOTAL MASS OF PROPELLANT TO BE USED: "+total_mass+" grams\nPropellant Formulation: Potassium Nitrate(KN)- 65% Sucrose-35%\nMASS OF EACH GRAIN BEFORE CASTING:\n Of 6cm standard with core: "+1.8*(2.0189*6*0.25*dm*dm)+"grams\n Of 3cm standard without core: "+1.8*(3.1415*3*0.25*dm*dm)+"grams\n Of 3cm with core: "+1.8*(2.0189*3*0.25*dm*dm)+"grams\\nMASS OF Potassium Nitrate(KN):"+0.65*total_mass+"grams\nMASS OF Sucrose(SU): "+0.35*total_mass+"grams\nBURN RATE: 3.96-4.00 mm/sec\nTHRUST: 600-620 kN\nSPECIFIC IMPULSE: 150-155 sec"}
                else
                if(prop_choice==3)
                {total_mass=1.676*(cn*6*2.0189*0.25*dm*dm + 3.1415*3*0.25*dm*dm + 3*2.0189*0.25*dm*dm)
                str+="\n\nTOTAL MASS OF PROPELLANT TO BE USED: "+total_mass+"grams\nPropellant Formulation: Ammonium Perchlorate(AP)-70% Polybutadiene Acrylonitrile(PBAN)-15% Iron(III) Oxide-3% Al Powder(metal fuel)-12% Additives-<=3%\nMASS OF EACH GRAIN BEFORE CASTING:\n Of 6cm standard with core: "+1.676*(2.0189*6*0.25*dm*dm)+"grams\n Of 3cm standard without core: "+1.676*(3.1415*3*0.25*dm*dm)+"grams\n Of 3cm with core: "+1.676*(2.0189*3*0.25*dm*dm)+"grams\nMASS OF Ammonium Perchlorate(AP): "+0.7*total_mass+"grams\nMASS OF Polybutadiene Acrylonitrile(PBAN): "+0.15*total_mass+"grams\nMASS OF Iron(III) Oxide: "+0.03*total_mass+"grams\nMASS OF Al Powder(metal fuel): "+0.12*total_mass+"grams\nMASS OF Additives: <="+0.03*total_mass+"grams\nBURN RATE: 1-3 mm/sec\nTHRUST: 4800 kN\nSPECIFIC IMPULSE: 260-265 sec"}
                else
                if(prop_choice==4)
                {total_mass=1.676*(cn*6*2.0189*0.25*dm*dm + 3.1415*3*0.25*dm*dm + 3*2.0189*0.25*dm*dm)
                 str+="\n\nTOTAL MASS OF PROPELLANT TO BE USED: "+total_mass+"grams\nPropellant Formulation: Ammonium Perchlorate(AP)-75% Polybutadiene Acrylonitrile(PBAN)-15% Iron(III) Oxide-3% Al Powder(metal fuel)-7% Additives-<=3%\nMASS OF EACH GRAIN BEFORE CASTING:\n Of 6cm standard with core: "+1.676*(2.0189*6*0.25*dm*dm)+"grams\n Of 3cm standard without core: "+1.676*(3.1415*3*0.25*dm*dm)+"grams\n Of 3cm with core: "+1.676*(2.0189*3*0.25*dm*dm)+"grams\nMASS OF Ammonium Perchlorate(AP): "+0.75*total_mass+"grams\nMASS OF Polybutadiene Acrylonitrile(PBAN): "+0.15*total_mass+"grams\nMASS OF Iron(III) Oxide: "+0.03*total_mass+"grams\nMASS OF Al Powder(metal fuel): "+0.07*total_mass+"grams\nMASS OF Additives: <="+0.03*total_mass+"grams\nBURN RATE: 1-3 mm/sec\nTHRUST: 4800 kN\nSPECIFIC IMPULSE: 260-265 sec"}
                else
                if(prop_choice==5)
                {total_mass=2*(cn*6*2.0189*0.25*dm*dm + 3.1415*3*0.25*dm*dm + 3*2.0189*0.25*dm*dm)
                str+="\n\nTOTAL MASS OF PROPELLANT TO BE USED: "+total_mass+"grams\nPropellant Formulation: Ammonium Nitrate(AN)-70% Glycidyl Azide Prepolymer(GAP)-15% Al Powder-10% Zinc Chloride-3% Iron(III) Oxide 2%\nMASS OF EACH GRAIN BEFORE CASTING:\n Of 6cm standard with core: "+2*(2.0189*6*0.25*dm*dm)+"grams\n Of 3cm standard without core: "+2*(3.1415*3*0.25*dm*dm)+"grams\n Of 3cm with core: "+2*(2.0189*3*0.25*dm*dm)+"grams\nMASS OF Ammonium Nitrate(AN): "+0.7*total_mass+"grams\nMASS OF Glycidyl Azide Prepolymer(GAP): "+0.15*total_mass+"grams\nMASS OF Al Powder: "+0.1*total_mass+"grams\nMASS OF Zinc Chloride: "+0.03*total_mass+"grams\nMASS OF Iron(III) Oxide: "+0.02*total_mass+"grams\nBURN RATE: To be tested\nTHRUST: To be tested\nSPECIFIC IMPULSE: To be tested"}

                str+="\n"+"TOTAL MASS USED : "+total_mass+" "+select_prop
            }
            else
            if (grain_length % 6<=3)
            {
                str="TOTAL NUMBER OF GRAINS: "+(cn+2)+"\n"+cn+"GRAINS OF 6CM WITH CORE \n 1 GRAIN OF 3 CM WITHOUT CORE \n 1 GRAIN OF "+(grain_length - 6*cn - 3)+" CM WITH CORE"
                if(prop_choice==0)
                {total_mass=1.8*(cn*2.0189*6*0.25*dm*dm + 3.1415*3*0.25*dm*dm + 2.0189*(grain_length - 6*cn - 3)*0.25*dm*dm)
                str+="\n\nTOTAL MASS OF PROPELLANT TO BE USED: "+total_mass+"grams\nPropellant Formulation: Potassium Nitrate(KN)- 70%  Sucrose(SU)-30%\nMASS OF EACH GRAIN BEFORE CASTING:\n Of 6cm standard with core: "+1.8*(2.0189*6*0.25*dm*dm)+"grams\n Of 3cm standard without core: "+1.8*(3.1415*3*0.25*dm*dm)+"grams\n Of "+(grain_length - 6*cn - 3)+"cm with core: "+1.8*(2.0189*(grain_length - 6*cn - 3)*0.25*dm*dm)+"grams\nMASS OF Potassium Nitrate(KN): "+0.7*total_mass+"grams\nMASS OF Sucrose(SU): "+0.3*total_mass+"grams\nBURN RATE: 3.96-4.00 mm/sec\nTHRUST: 600-620 kN\nSPECIFIC IMPULSE: 150-155 sec"}
                else
                if(prop_choice==1)
                {total_mass=1.83*(cn*2.0189*6*0.25*dm*dm + 3.1415*3*0.25*dm*dm + 2.0189*(grain_length - 6*cn - 3)*0.25*dm*dm)
                str+="\n\nTOTAL MASS OF PROPELLANT TO BE USED: "+total_mass+"grams\nPropellant Formulation: Potassium Nitrate(KN)- 65%  Sorbitol(SB)-10% Sucrose(SU)- 24% Carbon-1%\nMASS OF EACH GRAIN BEFORE CASTING:\n Of 6cm standard with core: "+1.83*(2.0189*6*0.25*dm*dm)+"grams\n Of 3cm standard without core: "+1.83*(3.1415*3*0.25*dm*dm)+"grams\n Of "+(grain_length-6*cn - 3)+"cm with core: "+1.83*(2.0189*(grain_length - 6*cn - 3)*0.25*dm*dm)+"grams\nMASS OF Potassium Nitrate(KN): "+0.65*total_mass+"grams\nMASS OF Sorbitol(SB): "+0.1*total_mass+"grams\nMASS OF Sucrose(SU): "+0.24*total_mass+"grams\nMASS OF Carbon: "+0.01*total_mass+"grams\nBURN RATE: 1.70-2.00 mm/sec\nTHRUST: 680-720 kN\nSPECIFIC IMPULSE: 160-165 sec"}
                else
                if(prop_choice==2)
                {total_mass=1.8*(cn*2.0189*6*0.25*dm*dm + 3.1415*3*0.25*dm*dm + 2.0189*(grain_length - 6*cn - 3)*0.25*dm*dm)
                str+="\n\nTOTAL MASS OF PROPELLANT TO BE USED: "+total_mass+"grams\nPropellant Formulation: Potassium Nitrate(KN)- 65% Sucrose-35%\nMASS OF EACH GRAIN BEFORE CASTING:\n Of 6cm standard with core: "+1.8*(2.0189*6*0.25*dm*dm)+"grams\n Of 3cm standard without core: "+1.8*(3.1415*3*0.25*dm*dm)+"grams\n Of "+(grain_length - 6*cn - 3)+"cm with core: "+1.8*(2.0189*(grain_length - 6*cn - 3)*0.25*dm*dm)+"grams\nMASS OF Potassium Nitrate(KN): "+0.65*total_mass+"grams\nMASS OF Sucrose(SU): "+0.35*total_mass+"grams\nBURN RATE: 3.96-4.00 mm/sec\nTHRUST: 600-620 kN\nSPECIFIC IMPULSE: 150-155 sec"}
                else
                if(prop_choice==3)
                {total_mass=1.676*(cn*2.0189*6*0.25*dm*dm + 3.1415*3*0.25*dm*dm + 2.0189*(grain_length - 6*cn - 3)*0.25*dm*dm)
                str+="\n\nTOTAL MASS OF PROPELLANT TO BE USED: "+total_mass+"grams\nPropellant Formulation: Ammonium Perchlorate(AP)-70% Polybutadiene Acrylonitrile(PBAN)-15% Iron(III) Oxide-3% Al Powder(metal fuel)-12% Additives-<=3%\nMASS OF EACH GRAIN BEFORE CASTING:\n Of 6cm standard with core: "+1.676*(2.0189*6*0.25*dm*dm)+"grams\n Of 3cm standard without core: "+1.676*(3.1415*3*0.25*dm*dm)+"grams\n Of "+(grain_length - 6*cn - 3)+"cm with core: "+1.676*(2.0189*(grain_length- 6*cn - 3)*0.25*dm*dm)+"grams\nMASS OF Ammonium Perchlorate(AP): "+0.7*total_mass+"grams\nMASS OF Polybutadiene Acrylonitrile(PBAN): "+0.15*total_mass+"grams\nMASS OF Iron(III) Oxide: "+0.03*total_mass+"grams\nMASS OF Al Powder(metal fuel): "+0.12*total_mass+"grams\nMASS OF Additives: <="+0.03*total_mass+"grams\nBURN RATE: 1-3 mm/sec\nTHRUST: 4800 kN\nSPECIFIC IMPULSE: 260-265 sec"}
                else
                if(prop_choice==4)
                {total_mass=1.676*(cn*2.0189*6*0.25*dm*dm + 3.1415*3*0.225*dm*dm + 2.0189*(grain_length - 6*cn - 3)*0.25*dm*dm)
                str+="\n\nTOTAL MASS OF PROPELLANT TO BE USED: "+total_mass+"grams\nPropellant Formulation: Ammonium Perchlorate(AP)-75% Polybutadiene Acrylonitrile(PBAN)-15% Iron(III) Oxide-3% Al Powder(metal fuel)-7% Additives-<=3%\nMASS OF EACH GRAIN BEFORE CASTING:\n Of 6cm standard with core: "+1.676*(2.0189*6*0.25*dm*dm)+"grams\n Of 3cm standard without core: "+1.676*(3.1415*3*0.25*dm*dm)+"grams\n Of "+(grain_length - 6*cn - 3)+"cm with core: "+1.676*(2.0189*(grain_length - 6*cn - 3)*0.25*dm*dm)+"grams\nMASS OF Ammonium Perchlorate(AP): "+0.75*total_mass+"grams\nMASS OF Polybutadiene Acrylonitrile(PBAN): "+0.15*total_mass+"grams\nMASS OF Iron(III) Oxide: "+0.03*total_mass+"grams\nMASS OF Al Powder(metal fuel): "+0.07*total_mass+"grams\nMASS OF Additives: <= "+0.03*total_mass+"grams\nBURN RATE: 1-3 mm/sec\nTHRUST: 4800 kN\nSPECIFIC IMPULSE: 260-265 sec"}
                else
                if(prop_choice==5)
                {total_mass=2*(cn*2.0189*6*0.25*dm*dm + 3.1415*3*0.25*dm*dm + 2.0189*(grain_length - 6*cn - 3)*0.25*dm*dm)
                str+="\n\nTOTAL MASS OF PROPELLANT TO BE USED: "+total_mass+"grams\nPropellant Formulation: Ammonium Nitrate(AN)-70% Glycidyl Azide Prepolymer(GAP)-15% Al Powder-10% Zinc Chloride-3% Iron(III) Oxide 2%\nMASS OF EACH GRAIN BEFORE CASTING:\n Of 6cm standard with core: "+2*(2.0189*6*0.25*dm*dm)+"grams\n Of 3cm standard without core: "+2*(3.1415*3*0.25*dm*dm)+"grams\n Of "+(grain_length - 6*cn - 3)+"cm with core: "+2*(2.0189*(grain_length - 6*cn - 3)*0.25*dm*dm)+"grams\nMASS OF Ammonium Nitrate(AN): "+0.7*total_mass+"grams\nMASS OF Glycidyl Azide Prepolymer(GAP): "+0.15*total_mass+"grams\nMASS OF Al Powder: "+0.1*total_mass+"grams\nMASS OF Zinc Chloride: "+0.03*total_mass+"grams\nMASS OF Iron(III) Oxide "+0.02*total_mass+"grams\nBURN RATE: To be tested\nTHRUST: To be tested\nSPECIFIC IMPULSE: To be tested"}
            }
            else
            if(grain_length%6==4)
            {str="TOTAL NUMBER OF GRAINS: "+(cn+2)+"\n"+(cn-1)+"GRAINS OF 6CM WITH CORE\n 1 GRAIN OF 3 CM WITHOUT CORE\n 2 GRAINS OF 6.5 CM WITH CORE"
                if(prop_choice==0)
                {total_mass=1.8*((cn-1)*2.0189*6*0.25*dm*dm + 3.1415*3*0.25*dm*dm + 2*2.0189*6.5*0.25*dm*dm)
                    str+="\n\nTOTAL MASS OF PROPELLANT TO BE USED: "+total_mass+"grams\nPropellant Formulation: Potassium Nitrate(KN)- 70%  Sucrose(SU)-30%\nMASS OF EACH GRAIN BEFORE CASTING:\n Of 6cm standard with core: "+1.8*(2.0189*6*0.25*dm*dm)+"grams\n Of 3cm standard without core: "+1.8*(3.1415*3*0.25*dm*dm)+"grams\n Of 6.5cm with core: "+1.8*(2.0189*6.5*0.25*dm*dm)+"grams\nMASS OF Potassium Nitrate(KN): "+0.7*total_mass+"grams\nMASS OF Sucrose(SU): "+0.3*total_mass+"grams\nBURN RATE: 3.96-4.00 mm/sec\nTHRUST: 600-620 kN\nSPECIFIC IMPULSE: 150-155 sec"}

                else
                    if(prop_choice==1)
                    {total_mass=1.83*((cn-1)*2.0189*6*0.25*dm*dm + 3.1415*3*0.25*dm*dm + 2*2.0189*6.5*0.25*dm*dm)
                        str+="\n\nTOTAL MASS OF PROPELLANT TO BE USED: "+total_mass+"grams\nPropellant Formulation: Potassium Nitrate(KN)- 65%  Sorbitol(SB)-10% Sucrose(SU)- 24% Carbon-1%\nMASS OF EACH GRAIN BEFORE CASTING:\n Of 6cm standard with core: "+1.83*(2.0189*6*0.25*dm*dm)+"grams\n Of 3cm standard without core: "+1.83*(3.1415*3*0.25*dm*dm)+"grams\n Of 6.5cm with core: "+1.83*(2.0189*6.5*0.25*dm*dm)+"grams\nMASS OF Potassium Nitrate(KN): "+0.65*total_mass+"grams\nMASS OF Sorbitol(SB): "+0.1*total_mass+"grams\nMASS OF Sucrose(SU): "+0.24*total_mass+"grams\nMASS OF Carbon: "+0.01*total_mass+"grams\nBURN RATE: 1.70-2.00 mm/sec\nTHRUST: 680-720 kN\nSPECIFIC IMPULSE: 160-165 sec"}
                    else
                        if(prop_choice==2)
                        {total_mass=1.8*((cn-1)*2.0189*6*0.25*dm*dm + 3.1415*3*0.25*dm*dm + 2*2.0189*6.5*0.25*dm*dm)
                            str+="\n\nTOTAL MASS OF PROPELLANT TO BE USED: "+total_mass+"grams\nPropellant Formulation: Potassium Nitrate(KN)- 65% Sucrose-35%\nMASS OF EACH GRAIN BEFORE CASTING:\n Of 6cm standard with core: "+1.8*(2.0189*6*0.25*dm*dm)+"grams\n Of 3cm standard without core: "+1.8*(3.1415*3*0.25*dm*dm)+"grams\n Of 6.5cm with core: "+1.8*(2.0189*6.5*0.25*dm*dm)+"grams\nMASS OF Potassium Nitrate(KN): "+0.65*total_mass+"grams\nMASS OF Sucrose(SU): "+0.35*total_mass+"grams\nBURN RATE: 3.96-4.00 mm/sec\nTHRUST: 600-620 kN\nSPECIFIC IMPULSE: 150-155 sec"}
                            else
                            if(prop_choice==3)
                            {total_mass=1.676*((cn-1)*2.0189*6*0.25*dm*dm + 3.1415*3*0.25*dm*dm + 2*2.0189*6.5*0.25*dm*dm)
                            str+="\n\nTOTAL MASS OF PROPELLANT TO BE USED: "+total_mass+"grams\nPropellant Formulation: Ammonium Perchlorate(AP)-70% Polybutadiene Acrylonitrile(PBAN)-15% Iron(III) Oxide-3% Al Powder(metal fuel)-12% Additives-<=3%\nMASS OF EACH GRAIN BEFORE CASTING:\n Of 6cm standard with core: "+1.676*(2.0189*6*0.25*dm*dm)+"grams\n Of 3cm standard without core: "+1.676*(3.1415*3*0.25*dm*dm)+"grams\n Of 6.5cm with core: "+1.676*(2.0189*6.5*0.25*dm*dm)+"grams\nMASS OF Ammonium Perchlorate(AP): "+0.7*total_mass+"grams\nMASS OF Polybutadiene Acrylonitrile(PBAN): "+0.15*total_mass+"grams\nMASS OF Iron(III) Oxide: "+0.03*total_mass+"grams\nMASS OF Al Powder(metal fuel): "+0.12*total_mass+"grams\nMASS OF Additives: <="+0.03*total_mass+"grams\nBURN RATE: 1-3 mm/sec\nTHRUST: 4800 kN\nSPECIFIC IMPULSE: 260-265 sec"}
                            else
                                if(prop_choice==4)
                                {total_mass=1.676*((cn-1)*2.0189*6*0.25*dm*dm + 3.1415*3*0.25*dm*dm + 2*2.0189*6.5*0.25*dm*dm)
                                str+="\n\nTOTAL MASS OF PROPELLANT TO BE USED: "+total_mass+"grams\nPropellant Formulation: Ammonium Perchlorate(AP)-75% Polybutadiene Acrylonitrile(PBAN)-15% Iron(III) Oxide-3% Al Powder(metal fuel)-7% Additives-<=3%\nMASS OF EACH GRAIN BEFORE CASTING:\n Of 6cm standard with core: "+1.676*(2.0189*6*0.25*dm*dm)+"grams\n Of 3cm standard without core: "+1.676*(3.1415*3*0.25*dm*dm)+"grams\n Of 6.5cm with core: "+1.676*(2.0189*6.5*0.25*dm*dm)+"grams\nMASS OF Ammonium Perchlorate(AP): "+0.75*total_mass+"grams\nMASS OF Polybutadiene Acrylonitrile(PBAN): "+0.15*total_mass+"grams\nMASS OF Iron(III) Oxide: "+0.03*total_mass+"grams\nMASS OF Al Powder(metal fuel): "+0.07*total_mass+"grams\nMASS OF Additives: <= "+0.03*total_mass+"grams\nBURN RATE: 1-3 mm/sec\nTHRUST: 4800 kN\nSPECIFIC IMPULSE: 260-265 sec"}
                                else
                                    if(prop_choice==5)
                                    {total_mass=2*((cn-1)*2.0189*6*0.25*dm*dm + 3.1415*3*0.25*dm*dm + 2*2.0189*6.5*0.25*dm*dm)
                                    str+="\n\nTOTAL MASS OF PROPELLANT TO BE USED: "+total_mass+"grams\nPropellant Formulation: Ammonium Nitrate(AN)-70% Glycidyl Azide Prepolymer(GAP)-15% Al Powder-10% Zinc Chloride-3% Iron(III) Oxide 2%\nMASS OF EACH GRAIN BEFORE CASTING:\n Of 6cm standard with core: "+2*(2.0189*6*0.25*dm*dm)+"grams\n Of 3cm standard without core: "+2*(3.1415*3*0.25*dm*dm)+"grams\n Of 6.5cm with core: "+2*(2.0189*6.5*0.25*dm*dm)+"grams\nMASS OF Ammonium Nitrate(AN): "+0.7*total_mass+"grams\nMASS OF Glycidyl Azide Prepolymer(GAP): "+0.15*total_mass+"grams\nMASS OF Al Powder: "+0.1*total_mass+"grams\nMASS OF Zinc Chloride: "+0.03*total_mass+"grams\nMASS OF Iron(III) Oxide "+0.02*total_mass+"grams\nBURN RATE: To be tested\nTHRUST: To be tested\nSPECIFIC IMPULSE: To be tested"}
            }
            else
                {
                    str="TOTAL NUMBER OF GRAINS: "+(cn+2)+"\n"+(cn-1)+"GRAINS OF 6CM WITH CORE\n 1 GRAIN OF 3 CM WITHOUT CORE\n 2 GRAINS OF 7 CM WITH CORE"
                    if(prop_choice==0)
                    {total_mass=1.8*((cn-1)*2.0189*6*0.25*dm*dm + 3.1415*3*0.25*dm*dm + 2*2.0189*7*0.25*dm*dm)
                    str+="\n\nTOTAL MASS OF PROPELLANT TO BE USED: "+total_mass+"grams\nPropellant Formulation: Potassium Nitrate(KN)- 70%  Sucrose(SU)-30%\nMASS OF EACH GRAIN BEFORE CASTING:\n Of 6cm standard with core: "+1.8*(2.0189*6*0.25*dm*dm)+"grams\n Of 3cm standard without core: "+1.8*(3.1415*3*0.25*dm*dm)+"grams\n Of 7cm with core: "+1.8*(2.0189*7*0.25*dm*dm)+"grams\nMASS OF Potassium Nitrate(KN): "+0.7*total_mass+"grams\nMASS OF Sucrose(SU): "+0.3*total_mass+"grams\nBURN RATE: 3.96-4.00 mm/sec\nTHRUST: 600-620 kN\nSPECIFIC IMPULSE: 150-155 sec"}
                    else
                        if(prop_choice==1)
                        {total_mass=1.83*((cn-1)*2.0189*6*0.25*dm*dm + 3.1415*3*0.25*dm*dm + 2*2.0189*7*0.25*dm*dm)
                        str+="\n\nTOTAL MASS OF PROPELLANT TO BE USED: "+total_mass+"grams\nPropellant Formulation: Potassium Nitrate(KN)- 65%  Sorbitol(SB)-10% Sucrose(SU)- 24% Carbon-1%\nMASS OF EACH GRAIN BEFORE CASTING:\n Of 6cm standard with core: "+1.83*(2.0189*6*0.25*dm*dm)+"grams\n Of 3cm standard without core: "+1.83*(3.1415*3*0.25*dm*dm)+"grams\n Of 7cm with core: "+1.83*(2.0189*7*0.25*dm*dm)+"grams\nMASS OF Potassium Nitrate(KN): "+0.65*total_mass+"grams\nMASS OF Sorbitol(SB): "+0.1*total_mass+"grams\nMASS OF Sucrose(SU): "+0.24*total_mass+"grams\nMASS OF Carbon: "+0.01*total_mass+"grams\nBURN RATE: 1.70-2.00 mm/sec\nTHRUST: 680-720 kN\nSPECIFIC IMPULSE: 160-165 sec"}
                        else
                            if(prop_choice==2)
                            {total_mass=1.8*((cn-1)*2.0189*6*0.25*dm*dm + 3.1415*3*0.25*dm*dm + 2*2.0189*7*0.25*dm*dm)
                            str+="\n\nTOTAL MASS OF PROPELLANT TO BE USED: "+total_mass+"grams\nPropellant Formulation: Potassium Nitrate(KN)- 65% Sucrose-35%\nMASS OF EACH GRAIN BEFORE CASTING:\n Of 6cm standard with core: "+1.8*(2.0189*6*0.25*dm*dm)+"grams\n Of 3cm standard without core: "+1.8*(3.1415*3*0.25*dm*dm)+"grams\n Of 7cm with core: "+1.8*(2.0189*7*0.25*dm*dm)+"grams\nMASS OF Potassium Nitrate(KN): "+0.65*total_mass+"grams\nMASS OF Sucrose(SU): "+0.35*total_mass+"grams\nBURN RATE: 3.96-4.00 mm/sec\nTHRUST: 600-620 kN\nSPECIFIC IMPULSE: 150-155 sec"}
                            else
                                if(prop_choice==3)
                                {total_mass=1.676*((cn-1)*2.0189*6*0.25*dm*dm + 3.1415*3*0.25*dm*dm + 2*2.0189*7*0.25*dm*dm)
                                str+="\n\nTOTAL MASS OF PROPELLANT TO BE USED: "+total_mass+"grams\nPropellant Formulation: Ammonium Perchlorate(AP)-70% Polybutadiene Acrylonitrile(PBAN)-15% Iron(III) Oxide-3% Al Powder(metal fuel)-12% Additives-<=3%\nMASS OF EACH GRAIN BEFORE CASTING:\n Of 6cm standard with core: "+1.676*(2.0189*6*0.25*dm*dm)+"grams\n Of 3cm standard without core: "+1.676*(3.1415*3*0.25*dm*dm)+"grams\n Of 7cm with core: "+1.676*(2.0189*7*0.25*dm*dm)+"grams\nMASS OF Ammonium Perchlorate(AP): "+0.7*total_mass+"grams\nMASS OF Polybutadiene Acrylonitrile(PBAN): "+0.15*total_mass+"grams\nMASS OF Iron(III) Oxide: "+0.03*total_mass+"grams\nMASS OF Al Powder(metal fuel): "+0.12*total_mass+"grams\nMASS OF Additives: <="+0.03*total_mass+"grams\nBURN RATE: 1-3 mm/sec\nTHRUST: 4800 kN\nSPECIFIC IMPULSE: 260-265 sec"}
                                else
                                    if(prop_choice==4)
                                    {total_mass=1.676*((cn-1)*2.0189*6*0.25*dm*dm + 3.1415*3*0.25*dm*dm + 2*2.0189*7*0.25*dm*dm)
                                    str+="\n\nTOTAL MASS OF PROPELLANT TO BE USED: "+total_mass+"grams\nPropellant Formulation: Ammonium Perchlorate(AP)-75% Polybutadiene Acrylonitrile(PBAN)-15% Iron(III) Oxide-3% Al Powder(metal fuel)-7% Additives-<=3%\nMASS OF EACH GRAIN BEFORE CASTING:\n Of 6cm standard with core: "+1.676*(2.0189*6*0.25*dm*dm)+"grams\n Of 3cm standard without core: "+1.676*(3.1415*3*0.25*dm*dm)+"grams\n Of 7cm with core: "+1.676*(2.0189*7*0.25*dm*dm)+"grams\nMASS OF Ammonium Perchlorate(AP): "+0.75*total_mass+"grams\nMASS OF Polybutadiene Acrylonitrile(PBAN): "+0.15*total_mass+"grams\nMASS OF Iron(III) Oxide: "+0.03*total_mass+"grams\nMASS OF Al Powder(metal fuel): "+0.07*total_mass+"grams\nMASS OF Additives: <= "+0.03*total_mass+"grams\nBURN RATE: 1-3 mm/sec\nTHRUST: 4800 kN\nSPECIFIC IMPULSE: 260-265 sec"}
                                         if(prop_choice==5)
                                         {total_mass=2*((cn-1)*2.0189*6*0.25*dm*dm + 3.1415*3*0.25*dm*dm + 2*2.0189*7*0.25*dm*dm)
                                         str+="\n\nTOTAL MASS OF PROPELLANT TO BE USED: "+total_mass+"grams\nPropellant Formulation: Ammonium Nitrate(AN)-70% Glycidyl Azide Prepolymer(GAP)-15% Al Powder-10% Zinc Chloride-3% Iron(III) Oxide 2%\nMASS OF EACH GRAIN BEFORE CASTING:\n Of 6cm standard with core: "+2*(2.0189*6*0.25*dm*dm)+"grams\n Of 3cm standard without core: "+2*(3.1415*3*0.25*dm*dm)+"grams\n Of 7cm with core: "+2*(2.0189*7*0.25*dm*dm)+"grams\nMASS OF Ammonium Nitrate(AN): "+0.7*total_mass+"grams\nMASS OF Glycidyl Azide Prepolymer(GAP): "+0.15*total_mass+"grams\nMASS OF Al Powder: "+0.1*total_mass+"grams\nMASS OF Zinc Chloride: "+0.03*total_mass+"grams\nMASS OF Iron(III) Oxide "+0.02*total_mass+"grams\nBURN RATE: To be tested\nTHRUST: To be tested\nSPECIFIC IMPULSE: To be tested"}

                }
        }
        else
        if (coreType=="round")
        {
            if (grain_length % 6 == 0)
            {
                if(prop_choice==0)
                {total_mass=1.8*(cn*6*3.1415*0.25*(dm*dm-dc*dc) + 3.1415*3*0.25*dm*dm + 3*3.1415*0.25*(dm*dm-dc*dc))
                    str+="\n\nTOTAL MASS OF PROPELLANT TO BE USED:"+total_mass+" grams\n Propellant Formulation: Potassium Nitrate(KN)- 70%  Sucrose(SU)-30%\nMASS OF EACH GRAIN BEFORE CASTING:\n Of 6cm standard with core: "+1.8*(3.1415*6*0.25*(dm*dm-dc*dc))+" grams\n Of 3cm standard without core: "+1.8*(3.1415*3*0.25*dm*dm)+"grams\n Of 3cm with core: "+1.8*(3.1415*3*0.25*(dm*dm-dc*dc))+"grams \nMASS OF Potassium Nitrate(KN): "+0.7*total_mass+" grams \nMASS OF Sucrose(SU):"+0.3*total_mass+"grams \nBURN RATE: 3.96-4.00 mm/sec \nTHRUST: 600-620 kN \nSPECIFIC IMPULSE: 150-155 sec"}
                else
                    if(prop_choice==1)
                    {total_mass=1.83*(cn*6*3.1415*0.25*(dm*dm-dc*dc) + 3.1415*3*0.25*dm*dm + 3*3.1415*0.25*(dm*dm-dc*dc))
                        str+="\n\nTOTAL MASS OF PROPELLANT TO BE USED: "+total_mass+" rams\nPropellant Formulation: Potassium Nitrate(KN)- 65%  Sorbitol(SB)-10% Sucrose(SU)- 24% Carbon-1%\nMASS OF EACH GRAIN BEFORE CASTING:\n Of 6cm standard with core: "+1.83*(3.1415*6*0.25*(dm*dm-dc*dc))+"grams\n Of 3cm standard without core: "+1.83*(3.1415*3*0.25*dm*dm)+"grams\n Of 3cm with core: "+1.83*(3.1415*3*0.25*(dm*dm-dc*dc))+"grams\nMASS OF Potassium Nitrate(KN): "+0.65*total_mass+"grams\nMASS OF Sorbitol(SB):"+0.1*total_mass+"grams\nMASS OF Sucrose(SU): "+0.24*total_mass+"grams\nMASS OF Carbon: "+0.01*total_mass+"grams\nBURN RATE: 1.70-2.00 mm/sec\nTHRUST: 680-720 kN\nSPECIFIC IMPULSE: 160-165 sec"}
                    else if(prop_choice==2)
                    {total_mass=1.8*(cn*6*3.1415*0.25*(dm*dm-dc*dc) + 3.1415*3*0.25*dm*dm + 3*3.1415*0.25*(dm*dm-dc*dc))
                        str+="\n\nTOTAL MASS OF PROPELLANT TO BE USED: "+total_mass+" grams\nPropellant Formulation: Potassium Nitrate(KN)- 65% Sucrose-35%\nMASS OF EACH GRAIN BEFORE CASTING:\n Of 6cm standard with core: "+1.8*(3.1415*6*0.25*(dm*dm-dc*dc))+"grams\n Of 3cm standard without core: "+1.8*(3.1415*3*0.25*dm*dm)+"grams\n Of 3cm with core: "+1.8*(2.0189*3*0.25*(dm*dm-dc*dc))+"grams\\nMASS OF Potassium Nitrate(KN):"+0.65*total_mass+"grams\nMASS OF Sucrose(SU): "+0.35*total_mass+"grams\nBURN RATE: 3.96-4.00 mm/sec\nTHRUST: 600-620 kN\nSPECIFIC IMPULSE: 150-155 sec"}
                    else
                        if(prop_choice==3)
                        {total_mass=1.676*(cn*6*3.1415*0.25*(dm*dm-dc*dc) + 3.1415*3*0.25*dm*dm + 3*3.1415*0.25*(dm*dm-dc*dc))
                            str+="\n\nTOTAL MASS OF PROPELLANT TO BE USED: "+total_mass+"grams\nPropellant Formulation: Ammonium Perchlorate(AP)-70% Polybutadiene Acrylonitrile(PBAN)-15% Iron(III) Oxide-3% Al Powder(metal fuel)-12% Additives-<=3%\nMASS OF EACH GRAIN BEFORE CASTING:\n Of 6cm standard with core: "+1.676*(3.1415*6*0.25*(dm*dm-dc*dc))+"grams\n Of 3cm standard without core: "+1.676*(3.1415*3*0.25*dm*dm)+"grams\n Of 3cm with core: "+1.676*(3.1415*3*0.25*(dm*dm-dc*dc))+"grams\nMASS OF Ammonium Perchlorate(AP): "+0.7*total_mass+"grams\nMASS OF Polybutadiene Acrylonitrile(PBAN): "+0.15*total_mass+"grams\nMASS OF Iron(III) Oxide: "+0.03*total_mass+"grams\nMASS OF Al Powder(metal fuel): "+0.12*total_mass+"grams\nMASS OF Additives: <="+0.03*total_mass+"grams\nBURN RATE: 1-3 mm/sec\nTHRUST: 4800 kN\nSPECIFIC IMPULSE: 260-265 sec"}
                        else
                            if(prop_choice==4)
                            {total_mass=1.676*(cn*6*3.1415*0.25*(dm*dm-dc*dc) + 3.1415*3*0.25*dm*dm + 3*3.1415*0.25*(dm*dm-dc*dc))
                                str+="\n\nTOTAL MASS OF PROPELLANT TO BE USED: "+total_mass+"grams\nPropellant Formulation: Ammonium Perchlorate(AP)-75% Polybutadiene Acrylonitrile(PBAN)-15% Iron(III) Oxide-3% Al Powder(metal fuel)-7% Additives-<=3%\nMASS OF EACH GRAIN BEFORE CASTING:\n Of 6cm standard with core: "+1.676*(3.1415*6*0.25*(dm*dm-dc*dc))+"grams\n Of 3cm standard without core: "+1.676*(3.1415*3*0.25*dm*dm)+"grams\n Of 3cm with core: "+1.676*(3.1415*3*0.25*(dm*dm-dc*dc))+"grams\nMASS OF Ammonium Perchlorate(AP): "+0.75*total_mass+"grams\nMASS OF Polybutadiene Acrylonitrile(PBAN): "+0.15*total_mass+"grams\nMASS OF Iron(III) Oxide: "+0.03*total_mass+"grams\nMASS OF Al Powder(metal fuel): "+0.07*total_mass+"grams\nMASS OF Additives: <="+0.03*total_mass+"grams\nBURN RATE: 1-3 mm/sec\nTHRUST: 4800 kN\nSPECIFIC IMPULSE: 260-265 sec"}
                            else
                                if(prop_choice==5)
                                {total_mass=2*(cn*6*3.1415*0.25*(dm*dm-dc*dc) + 3.1415*3*0.25*dm*dm + 3*3.1415*0.25*(dm*dm-dc*dc))
                                    str+="\n\nTOTAL MASS OF PROPELLANT TO BE USED: "+total_mass+"grams\nPropellant Formulation: Ammonium Nitrate(AN)-70% Glycidyl Azide Prepolymer(GAP)-15% Al Powder-10% Zinc Chloride-3% Iron(III) Oxide 2%\nMASS OF EACH GRAIN BEFORE CASTING:\n Of 6cm standard with core: "+2*(3.1415*6*0.25*(dm*dm-dc*dc))+"grams\n Of 3cm standard without core: "+2*(3.1415*3*0.25*dm*dm)+"grams\n Of 3cm with core: "+2*(3.1415*3*0.25*(dm*dm-dc*dc))+"grams\nMASS OF Ammonium Nitrate(AN): "+0.7*total_mass+"grams\nMASS OF Glycidyl Azide Prepolymer(GAP): "+0.15*total_mass+"grams\nMASS OF Al Powder: "+0.1*total_mass+"grams\nMASS OF Zinc Chloride: "+0.03*total_mass+"grams\nMASS OF Iron(III) Oxide: "+0.02*total_mass+"grams\nBURN RATE: To be tested\nTHRUST: To be tested\nSPECIFIC IMPULSE: To be tested"}

                str+="\n"+"TOTAL MASS USED : "+total_mass+" "+select_prop
            }
            else
                if (grain_length % 6<=3)
                {
                    str="TOTAL NUMBER OF GRAINS: "+(cn+2)+"\n"+cn+"GRAINS OF 6CM WITH CORE \n 1 GRAIN OF 3 CM WITHOUT CORE \n 1 GRAIN OF "+(grain_length - 6*cn - 3)+" CM WITH CORE"
                    if(prop_choice==0)
                    {total_mass=1.8*(cn*3.1415*6*0.25*(dm*dm-dc*dc) + 3.1415*3*0.25*dm*dm + 3.1415*(grain_length - 6*cn - 3)*0.25*(dm*dm-dc*dc))
                        str+="\n\nTOTAL MASS OF PROPELLANT TO BE USED: "+total_mass+"grams\nPropellant Formulation: Potassium Nitrate(KN)- 70%  Sucrose(SU)-30%\nMASS OF EACH GRAIN BEFORE CASTING:\n Of 6cm standard with core: "+1.8*(3.1415*6*0.25*(dm*dm-dc*dc))+"grams\n Of 3cm standard without core: "+1.8*(3.1415*3*0.25*dm*dm)+"grams\n Of "+(grain_length - 6*cn - 3)+"cm with core: "+1.8*(3.1415*(grain_length - 6*cn - 3)*0.25*(dm*dm-dc*dc))+"grams\nMASS OF Potassium Nitrate(KN): "+0.7*total_mass+"grams\nMASS OF Sucrose(SU): "+0.3*total_mass+"grams\nBURN RATE: 3.96-4.00 mm/sec\nTHRUST: 600-620 kN\nSPECIFIC IMPULSE: 150-155 sec"}
                    else
                        if(prop_choice==1)
                        {total_mass=1.83*(cn*3.1415*6*0.25*(dm*dm-dc*dc) + 3.1415*3*0.25*dm*dm + 3.1415*(grain_length - 6*cn - 3)*0.25*(dm*dm-dc*dc))
                            str+="\n\nTOTAL MASS OF PROPELLANT TO BE USED: "+total_mass+"grams\nPropellant Formulation: Potassium Nitrate(KN)- 65%  Sorbitol(SB)-10% Sucrose(SU)- 24% Carbon-1%\nMASS OF EACH GRAIN BEFORE CASTING:\n Of 6cm standard with core: "+1.83*(3.1415*6*0.25*(dm*dm-dc*dc))+"grams\n Of 3cm standard without core: "+1.83*(3.1415*3*0.25*dm*dm)+"grams\n Of "+(grain_length-6*cn - 3)+"cm with core: "+1.83*(3.1415*(grain_length - 6*cn - 3)*0.25*(dm*dm-dc*dc))+"grams\nMASS OF Potassium Nitrate(KN): "+0.65*total_mass+"grams\nMASS OF Sorbitol(SB): "+0.1*total_mass+"grams\nMASS OF Sucrose(SU): "+0.24*total_mass+"grams\nMASS OF Carbon: "+0.01*total_mass+"grams\nBURN RATE: 1.70-2.00 mm/sec\nTHRUST: 680-720 kN\nSPECIFIC IMPULSE: 160-165 sec"}
                        else
                            if(prop_choice==2)
                            {total_mass=1.8*(cn*3.1415*6*0.25*(dm*dm-dc*dc) + 3.1415*3*0.25*dm*dm + 3.1415*(grain_length - 6*cn - 3)*0.25*(dm*dm-dc*dc))
                                str+="\n\nTOTAL MASS OF PROPELLANT TO BE USED: "+total_mass+"grams\nPropellant Formulation: Potassium Nitrate(KN)- 65% Sucrose-35%\nMASS OF EACH GRAIN BEFORE CASTING:\n Of 6cm standard with core: "+1.8*(3.1415*6*0.25*(dm*dm-dc*dc))+"grams\n Of 3cm standard without core: "+1.8*(3.1415*3*0.25*dm*dm)+"grams\n Of "+(grain_length - 6*cn - 3)+"cm with core: "+1.8*(3.1415*(grain_length - 6*cn - 3)*0.25*(dm*dm-dc*dc))+"grams\nMASS OF Potassium Nitrate(KN): "+0.65*total_mass+"grams\nMASS OF Sucrose(SU): "+0.35*total_mass+"grams\nBURN RATE: 3.96-4.00 mm/sec\nTHRUST: 600-620 kN\nSPECIFIC IMPULSE: 150-155 sec"}
                            else
                                if(prop_choice==3)
                                {total_mass=1.676*(cn*3.1415*6*0.25*(dm*dm-dc*dc) + 3.1415*3*0.25*dm*dm + 3.1415*(grain_length - 6*cn - 3)*0.25*(dm*dm-dc*dc))
                                    str+="\n\nTOTAL MASS OF PROPELLANT TO BE USED: "+total_mass+"grams\nPropellant Formulation: Ammonium Perchlorate(AP)-70% Polybutadiene Acrylonitrile(PBAN)-15% Iron(III) Oxide-3% Al Powder(metal fuel)-12% Additives-<=3%\nMASS OF EACH GRAIN BEFORE CASTING:\n Of 6cm standard with core: "+1.676*(3.1415*6*0.25*(dm*dm-dc*dc))+"grams\n Of 3cm standard without core: "+1.676*(3.1415*3*0.25*dm*dm)+"grams\n Of "+(grain_length - 6*cn - 3)+"cm with core: "+1.676*(3.1415*(grain_length- 6*cn - 3)*0.25*(dm*dm-dc*dc))+"grams\nMASS OF Ammonium Perchlorate(AP): "+0.7*total_mass+"grams\nMASS OF Polybutadiene Acrylonitrile(PBAN): "+0.15*total_mass+"grams\nMASS OF Iron(III) Oxide: "+0.03*total_mass+"grams\nMASS OF Al Powder(metal fuel): "+0.12*total_mass+"grams\nMASS OF Additives: <="+0.03*total_mass+"grams\nBURN RATE: 1-3 mm/sec\nTHRUST: 4800 kN\nSPECIFIC IMPULSE: 260-265 sec"}
                                else
                                    if(prop_choice==4)
                                    {total_mass=1.676*(cn*3.1415*6*0.25*(dm*dm-dc*dc) + 3.1415*3*0.225*dm*dm + 3.1415*(grain_length - 6*cn - 3)*0.25*(dm*dm-dc*dc))
                                        str+="\n\nTOTAL MASS OF PROPELLANT TO BE USED: "+total_mass+"grams\nPropellant Formulation: Ammonium Perchlorate(AP)-75% Polybutadiene Acrylonitrile(PBAN)-15% Iron(III) Oxide-3% Al Powder(metal fuel)-7% Additives-<=3%\nMASS OF EACH GRAIN BEFORE CASTING:\n Of 6cm standard with core: "+1.676*(3.1415*6*0.25*(dm*dm-dc*dc))+"grams\n Of 3cm standard without core: "+1.676*(3.1415*3*0.25*dm*dm)+"grams\n Of "+(grain_length - 6*cn - 3)+"cm with core: "+1.676*(3.1415*(grain_length - 6*cn - 3)*0.25*(dm*dm-dc*dc))+"grams\nMASS OF Ammonium Perchlorate(AP): "+0.75*total_mass+"grams\nMASS OF Polybutadiene Acrylonitrile(PBAN): "+0.15*total_mass+"grams\nMASS OF Iron(III) Oxide: "+0.03*total_mass+"grams\nMASS OF Al Powder(metal fuel): "+0.07*total_mass+"grams\nMASS OF Additives: <= "+0.03*total_mass+"grams\nBURN RATE: 1-3 mm/sec\nTHRUST: 4800 kN\nSPECIFIC IMPULSE: 260-265 sec"}
                                    else
                                        if(prop_choice==5)
                                        {total_mass=2*(cn*3.1415*6*0.25*(dm*dm-dc*dc) + 3.1415*3*0.25*dm*dm + 3.1415*(grain_length - 6*cn - 3)*0.25*(dm*dm-dc*dc))
                                            str+="\n\nTOTAL MASS OF PROPELLANT TO BE USED: "+total_mass+"grams\nPropellant Formulation: Ammonium Nitrate(AN)-70% Glycidyl Azide Prepolymer(GAP)-15% Al Powder-10% Zinc Chloride-3% Iron(III) Oxide 2%\nMASS OF EACH GRAIN BEFORE CASTING:\n Of 6cm standard with core: "+2*(3.1415*6*0.25*(dm*dm-dc*dc))+"grams\n Of 3cm standard without core: "+2*(3.1415*3*0.25*dm*dm)+"grams\n Of "+(grain_length - 6*cn - 3)+"cm with core: "+2*(3.1415*(grain_length - 6*cn - 3)*0.25*(dm*dm-dc*dc))+"grams\nMASS OF Ammonium Nitrate(AN): "+0.7*total_mass+"grams\nMASS OF Glycidyl Azide Prepolymer(GAP): "+0.15*total_mass+"grams\nMASS OF Al Powder: "+0.1*total_mass+"grams\nMASS OF Zinc Chloride: "+0.03*total_mass+"grams\nMASS OF Iron(III) Oxide "+0.02*total_mass+"grams\nBURN RATE: To be tested\nTHRUST: To be tested\nSPECIFIC IMPULSE: To be tested"}
                }
                else
                    if(grain_length%6==4)
                    {str="TOTAL NUMBER OF GRAINS: "+(cn+2)+"\n"+(cn-1)+"GRAINS OF 6CM WITH CORE\n 1 GRAIN OF 3 CM WITHOUT CORE\n 2 GRAINS OF 6.5 CM WITH CORE"
                        if(prop_choice==0)
                        {total_mass=1.8*((cn-1)*3.1415*6*0.25*(dm*dm-dc*dc) + 3.1415*3*0.25*dm*dm + 2*3.1415*6.5*0.25*(dm*dm-dc*dc))
                            str+="\n\nTOTAL MASS OF PROPELLANT TO BE USED: "+total_mass+"grams\nPropellant Formulation: Potassium Nitrate(KN)- 70%  Sucrose(SU)-30%\nMASS OF EACH GRAIN BEFORE CASTING:\n Of 6cm standard with core: "+1.8*(3.1415*6*0.25*(dm*dm-dc*dc))+"grams\n Of 3cm standard without core: "+1.8*(3.1415*3*0.25*dm*dm)+"grams\n Of 6.5cm with core: "+1.8*(3.1415*6.5*0.25*(dm*dm-dc*dc))+"grams\nMASS OF Potassium Nitrate(KN): "+0.7*total_mass+"grams\nMASS OF Sucrose(SU): "+0.3*total_mass+"grams\nBURN RATE: 3.96-4.00 mm/sec\nTHRUST: 600-620 kN\nSPECIFIC IMPULSE: 150-155 sec"}

                        else
                            if(prop_choice==1)
                            {total_mass=1.83*((cn-1)*3.1415*6*0.25*(dm*dm-dc*dc) + 3.1415*3*0.25*dm*dm + 2*3.1415*6.5*0.25*(dm*dm-dc*dc))
                                str+="\n\nTOTAL MASS OF PROPELLANT TO BE USED: "+total_mass+"grams\nPropellant Formulation: Potassium Nitrate(KN)- 65%  Sorbitol(SB)-10% Sucrose(SU)- 24% Carbon-1%\nMASS OF EACH GRAIN BEFORE CASTING:\n Of 6cm standard with core: "+1.83*(3.1415*6*0.25*(dm*dm-dc*dc))+"grams\n Of 3cm standard without core: "+1.83*(3.1415*3*0.25*dm*dm)+"grams\n Of 6.5cm with core: "+1.83*(3.1415*6.5*0.25*(dm*dm-dc*dc))+"grams\nMASS OF Potassium Nitrate(KN): "+0.65*total_mass+"grams\nMASS OF Sorbitol(SB): "+0.1*total_mass+"grams\nMASS OF Sucrose(SU): "+0.24*total_mass+"grams\nMASS OF Carbon: "+0.01*total_mass+"grams\nBURN RATE: 1.70-2.00 mm/sec\nTHRUST: 680-720 kN\nSPECIFIC IMPULSE: 160-165 sec"}
                            else
                                if(prop_choice==2)
                                {total_mass=1.8*((cn-1)*3.1415*6*0.25*(dm*dm-dc*dc) + 3.1415*3*0.25*dm*dm + 2*3.1415*6.5*0.25*(dm*dm-dc*dc))
                                    str+="\n\nTOTAL MASS OF PROPELLANT TO BE USED: "+total_mass+"grams\nPropellant Formulation: Potassium Nitrate(KN)- 65% Sucrose-35%\nMASS OF EACH GRAIN BEFORE CASTING:\n Of 6cm standard with core: "+1.8*(3.1415*6*0.25*(dm*dm-dc*dc))+"grams\n Of 3cm standard without core: "+1.8*(3.1415*3*0.25*dm*dm)+"grams\n Of 6.5cm with core: "+1.8*(3.1415*6.5*0.25*(dm*dm-dc*dc))+"grams\nMASS OF Potassium Nitrate(KN): "+0.65*total_mass+"grams\nMASS OF Sucrose(SU): "+0.35*total_mass+"grams\nBURN RATE: 3.96-4.00 mm/sec\nTHRUST: 600-620 kN\nSPECIFIC IMPULSE: 150-155 sec"}
                                else
                                    if(prop_choice==3)
                                    {total_mass=1.676*((cn-1)*3.1415*6*0.25*(dm*dm-dc*dc) + 3.1415*3*0.25*dm*dm + 2*3.1415*6.5*0.25*(dm*dm-dc*dc))
                                        str+="\n\nTOTAL MASS OF PROPELLANT TO BE USED: "+total_mass+"grams\nPropellant Formulation: Ammonium Perchlorate(AP)-70% Polybutadiene Acrylonitrile(PBAN)-15% Iron(III) Oxide-3% Al Powder(metal fuel)-12% Additives-<=3%\nMASS OF EACH GRAIN BEFORE CASTING:\n Of 6cm standard with core: "+1.676*(3.1415*6*0.25*(dm*dm-dc*dc))+"grams\n Of 3cm standard without core: "+1.676*(3.1415*3*0.25*dm*dm)+"grams\n Of 6.5cm with core: "+1.676*(3.1415*6.5*0.25*(dm*dm-dc*dc))+"grams\nMASS OF Ammonium Perchlorate(AP): "+0.7*total_mass+"grams\nMASS OF Polybutadiene Acrylonitrile(PBAN): "+0.15*total_mass+"grams\nMASS OF Iron(III) Oxide: "+0.03*total_mass+"grams\nMASS OF Al Powder(metal fuel): "+0.12*total_mass+"grams\nMASS OF Additives: <="+0.03*total_mass+"grams\nBURN RATE: 1-3 mm/sec\nTHRUST: 4800 kN\nSPECIFIC IMPULSE: 260-265 sec"}
                                    else
                                        if(prop_choice==4)
                                        {total_mass=1.676*((cn-1)*3.1415*6*0.25*(dm*dm-dc*dc) + 3.1415*3*0.25*dm*dm + 2*3.1415*6.5*0.25*(dm*dm-dc*dc))
                                            str+="\n\nTOTAL MASS OF PROPELLANT TO BE USED: "+total_mass+"grams\nPropellant Formulation: Ammonium Perchlorate(AP)-75% Polybutadiene Acrylonitrile(PBAN)-15% Iron(III) Oxide-3% Al Powder(metal fuel)-7% Additives-<=3%\nMASS OF EACH GRAIN BEFORE CASTING:\n Of 6cm standard with core: "+1.676*(3.1415*6*0.25*(dm*dm-dc*dc))+"grams\n Of 3cm standard without core: "+1.676*(3.1415*3*0.25*dm*dm)+"grams\n Of 6.5cm with core: "+1.676*(3.1415*6.5*0.25*(dm*dm-dc*dc))+"grams\nMASS OF Ammonium Perchlorate(AP): "+0.75*total_mass+"grams\nMASS OF Polybutadiene Acrylonitrile(PBAN): "+0.15*total_mass+"grams\nMASS OF Iron(III) Oxide: "+0.03*total_mass+"grams\nMASS OF Al Powder(metal fuel): "+0.07*total_mass+"grams\nMASS OF Additives: <= "+0.03*total_mass+"grams\nBURN RATE: 1-3 mm/sec\nTHRUST: 4800 kN\nSPECIFIC IMPULSE: 260-265 sec"}
                                        else
                                            if(prop_choice==5)
                                            {total_mass=2*((cn-1)*3.1415*6*0.25*(dm*dm-dc*dc) + 3.1415*3*0.25*dm*dm + 2*3.1415*6.5*0.25*(dm*dm-dc*dc))
                                                str+="\n\nTOTAL MASS OF PROPELLANT TO BE USED: "+total_mass+"grams\nPropellant Formulation: Ammonium Nitrate(AN)-70% Glycidyl Azide Prepolymer(GAP)-15% Al Powder-10% Zinc Chloride-3% Iron(III) Oxide 2%\nMASS OF EACH GRAIN BEFORE CASTING:\n Of 6cm standard with core: "+2*(3.1415*6*0.25*(dm*dm-dc*dc))+"grams\n Of 3cm standard without core: "+2*(3.1415*3*0.25*dm*dm)+"grams\n Of 6.5cm with core: "+2*(3.1415*6.5*0.25*(dm*dm-dc*dc))+"grams\nMASS OF Ammonium Nitrate(AN): "+0.7*total_mass+"grams\nMASS OF Glycidyl Azide Prepolymer(GAP): "+0.15*total_mass+"grams\nMASS OF Al Powder: "+0.1*total_mass+"grams\nMASS OF Zinc Chloride: "+0.03*total_mass+"grams\nMASS OF Iron(III) Oxide "+0.02*total_mass+"grams\nBURN RATE: To be tested\nTHRUST: To be tested\nSPECIFIC IMPULSE: To be tested"}
                    }
                    else
                    {
                        str="TOTAL NUMBER OF GRAINS: "+(cn+2)+"\n"+(cn-1)+"GRAINS OF 6CM WITH CORE\n 1 GRAIN OF 3 CM WITHOUT CORE\n 2 GRAINS OF 7 CM WITH CORE"
                        if(prop_choice==0)
                        {total_mass=1.8*((cn-1)*3.1415*6*0.25*(dm*dm-dc*dc) + 3.1415*3*0.25*dm*dm + 2*3.1415*7*0.25*(dm*dm-dc*dc))
                            str+="\n\nTOTAL MASS OF PROPELLANT TO BE USED: "+total_mass+"grams\nPropellant Formulation: Potassium Nitrate(KN)- 70%  Sucrose(SU)-30%\nMASS OF EACH GRAIN BEFORE CASTING:\n Of 6cm standard with core: "+1.8*(3.1415*6*0.25*(dm*dm-dc*dc))+"grams\n Of 3cm standard without core: "+1.8*(3.1415*3*0.25*dm*dm)+"grams\n Of 7cm with core: "+1.8*(3.1415*7*0.25*(dm*dm-dc*dc))+"grams\nMASS OF Potassium Nitrate(KN): "+0.7*total_mass+"grams\nMASS OF Sucrose(SU): "+0.3*total_mass+"grams\nBURN RATE: 3.96-4.00 mm/sec\nTHRUST: 600-620 kN\nSPECIFIC IMPULSE: 150-155 sec"}

                        else
                            if(prop_choice==1)
                            {total_mass=1.83*((cn-1)*3.1415*6*0.25*(dm*dm-dc*dc) + 3.1415*3*0.25*dm*dm + 2*3.1415*7*0.25*(dm*dm-dc*dc))
                                str+="\n\nTOTAL MASS OF PROPELLANT TO BE USED: "+total_mass+"grams\nPropellant Formulation: Potassium Nitrate(KN)- 65%  Sorbitol(SB)-10% Sucrose(SU)- 24% Carbon-1%\nMASS OF EACH GRAIN BEFORE CASTING:\n Of 6cm standard with core: "+1.83*(3.1415*6*0.25*(dm*dm-dc*dc))+"grams\n Of 3cm standard without core: "+1.83*(3.1415*3*0.25*dm*dm)+"grams\n Of 7cm with core: "+1.83*(3.1415*7*0.25*(dm*dm-dc*dc))+"grams\nMASS OF Potassium Nitrate(KN): "+0.65*total_mass+"grams\nMASS OF Sorbitol(SB): "+0.1*total_mass+"grams\nMASS OF Sucrose(SU): "+0.24*total_mass+"grams\nMASS OF Carbon: "+0.01*total_mass+"grams\nBURN RATE: 1.70-2.00 mm/sec\nTHRUST: 680-720 kN\nSPECIFIC IMPULSE: 160-165 sec"}
                            else
                                if(prop_choice==2)
                                {total_mass=1.8*((cn-1)*3.1415*6*0.25*(dm*dm-dc*dc)+ 3.1415*3*0.25*dm*dm + 2*3.1415*7*0.25*(dm*dm-dc*dc))
                                    str+="\n\nTOTAL MASS OF PROPELLANT TO BE USED: "+total_mass+"grams\nPropellant Formulation: Potassium Nitrate(KN)- 65% Sucrose-35%\nMASS OF EACH GRAIN BEFORE CASTING:\n Of 6cm standard with core: "+1.8*(3.1415*6*0.25*(dm*dm-dc*dc))+"grams\n Of 3cm standard without core: "+1.8*(3.1415*3*0.25*dm*dm)+"grams\n Of 7cm with core: "+1.8*(3.1415*7*0.25*(dm*dm-dc*dc))+"grams\nMASS OF Potassium Nitrate(KN): "+0.65*total_mass+"grams\nMASS OF Sucrose(SU): "+0.35*total_mass+"grams\nBURN RATE: 3.96-4.00 mm/sec\nTHRUST: 600-620 kN\nSPECIFIC IMPULSE: 150-155 sec"}
                                else
                                    if(prop_choice==3)
                                    {total_mass=1.676*((cn-1)*3.1415*6*0.25*(dm*dm-dc*dc) + 3.1415*3*0.25*dm*dm + 2*3.1415*7*0.25*(dm*dm-dc*dc))
                                        str+="\n\nTOTAL MASS OF PROPELLANT TO BE USED: "+total_mass+"grams\nPropellant Formulation: Ammonium Perchlorate(AP)-70% Polybutadiene Acrylonitrile(PBAN)-15% Iron(III) Oxide-3% Al Powder(metal fuel)-12% Additives-<=3%\nMASS OF EACH GRAIN BEFORE CASTING:\n Of 6cm standard with core: "+1.676*(3.1415*6*0.25*(dm*dm-dc*dc))+"grams\n Of 3cm standard without core: "+1.676*(3.1415*3*0.25*dm*dm)+"grams\n Of 7cm with core: "+1.676*(3.1415*7*0.25*(dm*dm-dc*dc))+"grams\nMASS OF Ammonium Perchlorate(AP): "+0.7*total_mass+"grams\nMASS OF Polybutadiene Acrylonitrile(PBAN): "+0.15*total_mass+"grams\nMASS OF Iron(III) Oxide: "+0.03*total_mass+"grams\nMASS OF Al Powder(metal fuel): "+0.12*total_mass+"grams\nMASS OF Additives: <="+0.03*total_mass+"grams\nBURN RATE: 1-3 mm/sec\nTHRUST: 4800 kN\nSPECIFIC IMPULSE: 260-265 sec"}
                                    else
                                        if(prop_choice==4)
                                        {total_mass=1.676*((cn-1)*3.1415*6*0.25*(dm*dm-dc*dc) + 3.1415*3*0.25*dm*dm + 2*3.1415*7*0.25*(dm*dm-dc*dc))
                                            str+="\n\nTOTAL MASS OF PROPELLANT TO BE USED: "+total_mass+"grams\nPropellant Formulation: Ammonium Perchlorate(AP)-75% Polybutadiene Acrylonitrile(PBAN)-15% Iron(III) Oxide-3% Al Powder(metal fuel)-7% Additives-<=3%\nMASS OF EACH GRAIN BEFORE CASTING:\n Of 6cm standard with core: "+1.676*(3.1415*6*0.25*(dm*dm-dc*dc))+"grams\n Of 3cm standard without core: "+1.676*(3.1415*3*0.25*dm*dm)+"grams\n Of 7cm with core: "+1.676*(3.1415*7*0.25*(dm*dm-dc*dc))+"grams\nMASS OF Ammonium Perchlorate(AP): "+0.75*total_mass+"grams\nMASS OF Polybutadiene Acrylonitrile(PBAN): "+0.15*total_mass+"grams\nMASS OF Iron(III) Oxide: "+0.03*total_mass+"grams\nMASS OF Al Powder(metal fuel): "+0.07*total_mass+"grams\nMASS OF Additives: <= "+0.03*total_mass+"grams\nBURN RATE: 1-3 mm/sec\nTHRUST: 4800 kN\nSPECIFIC IMPULSE: 260-265 sec"}
                                        else
                                            if(prop_choice==5)
                                            {total_mass=2*((cn-1)*3.1415*6*0.25*(dm*dm-dc*dc) + 3.1415*3*0.25*dm*dm + 2*3.1415*7*0.25*(dm*dm-dc*dc))
                                                str+="\n\nTOTAL MASS OF PROPELLANT TO BE USED: "+total_mass+"grams\nPropellant Formulation: Ammonium Nitrate(AN)-70% Glycidyl Azide Prepolymer(GAP)-15% Al Powder-10% Zinc Chloride-3% Iron(III) Oxide 2%\nMASS OF EACH GRAIN BEFORE CASTING:\n Of 6cm standard with core: "+2*(3.1415*6*0.25*(dm*dm-dc*dc))+"grams\n Of 3cm standard without core: "+2*(3.1415*3*0.25*dm*dm)+"grams\n Of 7cm with core: "+2*(3.1415*7*0.25*(dm*dm-dc*dc))+"grams\nMASS OF Ammonium Nitrate(AN): "+0.7*total_mass+"grams\nMASS OF Glycidyl Azide Prepolymer(GAP): "+0.15*total_mass+"grams\nMASS OF Al Powder: "+0.1*total_mass+"grams\nMASS OF Zinc Chloride: "+0.03*total_mass+"grams\nMASS OF Iron(III) Oxide "+0.02*total_mass+"grams\nBURN RATE: To be tested\nTHRUST: To be tested\nSPECIFIC IMPULSE: To be tested"}

                    }
        }
        val results=view.findViewById<TextView>(R.id.pop_message)
        results . setText(str)

        Log.d("message",""+length)
        Log.d("message",""+select_prop)
        Log.d("message",""+coreType)
        Log.d("message",""+dm)

    }
}
