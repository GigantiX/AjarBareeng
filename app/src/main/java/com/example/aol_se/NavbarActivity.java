package com.example.aol_se;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
//import meow.bottomnavigation.MeowBottomNavigation;

public class NavbarActivity extends AppCompatActivity {

    private MeowBottomNavigation meowBottomNavigation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navbar);

        meowBottomNavigation = findViewById(R.id.navbar_meowNavbar);
        meowBottomNavigation.show(1,true);

        meowBottomNavigation.add(new MeowBottomNavigation.Model(1, R.drawable.icon_home));
        meowBottomNavigation.add(new MeowBottomNavigation.Model(2, R.drawable.icon_favorite));
        meowBottomNavigation.add(new MeowBottomNavigation.Model(3, R.drawable.icon_course2));
        meowBottomNavigation.add(new MeowBottomNavigation.Model(4, R.drawable.icon_notification));
        meowBottomNavigation.add(new MeowBottomNavigation.Model(5, R.drawable.icon_settings));

        meowBottomNavigation.setOnShowListener(new Function1<MeowBottomNavigation.Model, Unit>() {
            @Override
            public Unit invoke(MeowBottomNavigation.Model model) {
                Fragment fragment = null;

                switch (model.getId()){
                    case 1:
                        fragment = new HomeFragment();
                        break;

                    case 2:
                        fragment = new FavoriteFragment();
                        break;

                    case 3:
                        fragment = new CourseFragment();
                        break;

                    case 4:
                        fragment = new NotificationFragment();
                        break;

                    case 5:
                        fragment = new SettingsFragment();
                        break;
                }
                loadFragment(fragment);
                return null;
            }
        });

        meowBottomNavigation.setOnClickMenuListener(new Function1<MeowBottomNavigation.Model, Unit>() {
            @Override
            public Unit invoke(MeowBottomNavigation.Model model) {
                switch (model.getId()){
                    case 1:
                        break;

                    case 2:
                        break;

                    case 3:
                        break;

                    case 4:
                        break;

                    case 5:
                        break;
                }
                return null;
            }
        });
    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.navbar_frameLay, fragment).commit();
    }
}