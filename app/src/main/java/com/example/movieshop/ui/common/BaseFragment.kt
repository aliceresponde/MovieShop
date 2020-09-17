package com.example.movieshop.ui.common

import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

open class BaseFragment<T : ViewBinding> : Fragment() {
    private var _binding: T? = null
    protected var binding
        get() = _binding!!
        set(value) {
            _binding = value
        }
}