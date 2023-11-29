package com.example.web.controllers;

import com.example.web.Enum.TipoLancamento;
import com.example.web.domain.Lancamento;
import com.example.web.service.LancamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/")
public class HomeController {

    @Autowired
    LancamentoService lancamentoService;

    @GetMapping
    public ModelAndView listaLancamento(ModelMap model) {
        ModelAndView modelAndView = new ModelAndView("index");
        if (model.containsAttribute("lancamentos"))
            modelAndView.addObject("lancamentos", model.getAttribute("lancamentos"));
        else {
            modelAndView.addObject("lancamentos", lancamentoService.listAll());
        }

        List<Lancamento> ultimasReceitas = lancamentoService.listByTipo(TipoLancamento.RECEITA);
        modelAndView.addObject("ultimasReceitas", ultimasReceitas);

        List<Lancamento> ultimasDespesas = lancamentoService.listByTipo(TipoLancamento.DESPESA);
        modelAndView.addObject("ultimasDespesas", ultimasDespesas);

        double totalReceita = ultimasReceitas.stream().mapToDouble(l -> l.getValor().doubleValue()).sum();
        double totalDespesa = ultimasDespesas.stream().mapToDouble(l -> l.getValor().doubleValue()).sum();
        double totalLiquido = totalReceita - totalDespesa;

        modelAndView.addObject("totalReceita", totalReceita);
        modelAndView.addObject("totalDespesa", totalDespesa);
        modelAndView.addObject("totalLiquido", totalLiquido);

        return modelAndView;
    }
}
