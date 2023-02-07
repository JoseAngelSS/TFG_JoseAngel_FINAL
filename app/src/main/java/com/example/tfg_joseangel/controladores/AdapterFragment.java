package com.example.tfg_joseangel.controladores;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.tfg_joseangel.Fragments.LoginFragment;
import com.example.tfg_joseangel.Fragments.fragment_register;

public class AdapterFragment extends FragmentStateAdapter {

    public AdapterFragment(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull

    public Fragment createFragment(int position) {
        switch(position){
            case 0: return new LoginFragment();
            default: return new fragment_register();

        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
