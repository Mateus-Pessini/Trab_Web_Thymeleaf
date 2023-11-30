package com.example.web.controllers;

import com.example.web.Enum.TipoLancamento;
import com.example.web.domain.Lancamento;
import com.example.web.service.CategoriaService;
import com.example.web.service.LancamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(path = "/lancamento")
public class LancamentoController {

    @Autowired
    private LancamentoService lancamentoService;

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping
    public ModelAndView listaLancamento(ModelMap model) {
        ModelAndView modelAndView = new ModelAndView("lancamento/listar");
        if (model.containsAttribute("lancamentos"))
            modelAndView.addObject("lancamentos", model.getAttribute("lancamentos"));
        else {
            modelAndView.addObject("lancamentos", lancamentoService.listAll());
        }

        return modelAndView;
    }

    @GetMapping(path = "/filtrar")
    public String filtrarLancamentos(@RequestParam(name = "dataInicial", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicial,
                                     @RequestParam(name = "dataFinal", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFinal,
                                     @RequestParam(name = "tipo", required = false) TipoLancamento tipo,
                                     RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("lancamentos", lancamentoService.listByFilter(dataInicial, dataFinal, tipo));
        return "redirect:/lancamento";
    }

    @GetMapping(path = "/editar/{id}")
    public ModelAndView editarLancamento(@PathVariable("id") Long id) {
        ModelAndView modelAndView = new ModelAndView("lancamento/inserir");
        modelAndView.addObject("lancamento", lancamentoService.findById(id));
        modelAndView.addObject("categorias", categoriaService.listAll());
        return modelAndView;
    }

    @GetMapping(path = "/remover/{id}")
    public String removerLancamento(@PathVariable("id") Long id) {
        lancamentoService.delete(id);
        return "redirect:/lancamento";
    }

    @PostMapping
    public String salvarLancamento(@Valid Lancamento lancamento,
                                   BindingResult bindingResult,
                                   RedirectAttributes redirectAttributes){

        List<String> msg = new ArrayList<>();

        if (bindingResult.hasErrors() || !msg.isEmpty()) {
            redirectAttributes.addFlashAttribute("lancamento", lancamento);

            for (ObjectError objectError : bindingResult.getAllErrors()) {
                msg.add(
                        ((FieldError) objectError).getField() + " " +
                                objectError.getDefaultMessage());
            }

            redirectAttributes.addFlashAttribute("msg", msg);

            return "redirect:/lancamento/criar";
        }

        lancamentoService.insert(lancamento);

        return "redirect:/lancamento";
    }

    @GetMapping(path = "/criar")
    public ModelAndView retornaNovaLancamento(ModelMap model) {
        ModelAndView modelAndView = new ModelAndView("lancamento/inserir");

        if (model.containsAttribute("lancamento")) {
            modelAndView.addObject("lancamento", model.getAttribute("lancamento"));
            modelAndView.addObject("msg", model.getAttribute("msg"));

        } else {
            modelAndView.addObject("lancamento", new Lancamento());
            modelAndView.addObject("msg", new ArrayList<String>());
        }

        // Adicionar a lista de categorias ao modelo
        modelAndView.addObject("categorias", categoriaService.listAll());

        return modelAndView;
    }


}
