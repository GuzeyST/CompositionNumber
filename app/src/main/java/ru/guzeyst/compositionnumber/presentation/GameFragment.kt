package ru.guzeyst.compositionnumber.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModelProvider
import ru.guzeyst.compositionnumber.R
import ru.guzeyst.compositionnumber.databinding.FragmentGameBinding
import ru.guzeyst.compositionnumber.domain.entity.GameResult
import ru.guzeyst.compositionnumber.domain.entity.GameSettings
import ru.guzeyst.compositionnumber.domain.entity.Level

class GameFragment : Fragment() {

    private var _binding: FragmentGameBinding? = null
    private val binding: FragmentGameBinding
        get() = _binding ?: throw RuntimeException("FragmentGameBinding is NULL")
    private lateinit var level: Level
    private val viewModel by lazy {
        ViewModelProvider(
            this
        )[GameViewModel::class.java]
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        viewModel.startGame(level)
        viewModel.minPercent.observe(viewLifecycleOwner, {
            binding.tvLeftNumber.text = "fhh"
        })
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseArgs()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvLeftNumber.setOnClickListener {
            launchFinishFragment(GameResult(
                true,
                4,
                3,
                GameSettings(
                    3,
                    5,
                    3,
                    1,
                )
            ))
        }
    }

    private fun launchFinishFragment(gameResult: GameResult){
        requireActivity().supportFragmentManager
            .beginTransaction()
            .addToBackStack(null)
            .replace(
                R.id.main_container,
                GameFinishedFragment.getInstance(gameResult)
            )
            .commit()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun parseArgs(){
        requireArguments().getParcelable<Level>(KEY_LEVEL)?.let {
            level = it
        }
    }

    companion object{

        private const val KEY_LEVEL = "level"
        const val NAME_GAME_FRAGMENT = "game_fragment"

        fun newInstance(level: Level): GameFragment{
            return GameFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(KEY_LEVEL, level)
                }
            }
        }
    }
}
