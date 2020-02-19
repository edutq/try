package com.us.skyguardian.translock.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.us.skyguardian.translock.R;

public class BaseFragment extends Fragment {

    private int layoutRes;
    private int toolbarId;
    private int navHostId;
    private Toolbar toolbar;
    private AppBarConfiguration appBarConfiguration;
    private NavController navController;

    public BaseFragment (int layout, int toolbar, int navhost) {

        this.layoutRes = layout;
        this.toolbarId = toolbar;
        this.navHostId = navhost;


    }

    @Override
    public void onStart() {
        super.onStart();
        toolbar = getActivity().findViewById(this.toolbarId);
        navController = Navigation.findNavController(getActivity(), navHostId);
        appBarConfiguration = new AppBarConfiguration.Builder(
                navController.getGraph())
                .build();
        NavigationUI.setupWithNavController(toolbar, navController, appBarConfiguration);

        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {


                if(destination.getId() == R.id.compartidosFragment) {

                    toolbar.getMenu().clear();
                    toolbar.inflateMenu(R.menu.barra_compartidos);

                } else if (destination.getId() == R.id.candadosFragment) {

                    toolbar.getMenu().clear();
                    toolbar.inflateMenu(R.menu.barra_candados);

                } else if (destination.getId() == R.id.historialFragment) {

                    toolbar.getMenu().clear();
                    toolbar.inflateMenu(R.menu.barra_historial);

                } else if (destination.getId() == R.id.usuariosCompartidosFragment) {

                    toolbar.getMenu().clear();
                    toolbar.inflateMenu(R.menu.barra_compartidos);

                } else {
                    toolbar.getMenu().clear();
                }

            }
        });

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(this.layoutRes, container, false);



        return view;
    }

    public Boolean onBackPress() {

        return Navigation.findNavController(getActivity(), this.navHostId).navigateUp();

    }

    public NavController getNavController() {

        return Navigation.findNavController(getActivity(), this.navHostId);

    }

    public AppBarConfiguration getAppBarConfiguration () {

        return this.appBarConfiguration;
    }

    public Toolbar getToolbar () {
        return this.toolbar;
    }



}
