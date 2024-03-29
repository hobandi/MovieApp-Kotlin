package pl.kfert.movie.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import pl.kfert.movie.databinding.DetailFragmentBinding

class DetailFragment : Fragment()  {

    private val viewModel : DetailFragmentViewModel by viewModel{ parametersOf(args.movie, args.save)}

    private val args : DetailFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {

        val binding = DetailFragmentBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        binding.buttonFavorite.setOnClickListener{ view ->
            viewModel.setFavorite(view.isSelected.not())
        }

        context ?: return binding.root

        return binding.root
    }

}
