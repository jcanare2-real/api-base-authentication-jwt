package com.jcanare2.cursoapis.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jcanare2.cursoapis.entity.Message;

@RestController
@RequestMapping("/api/messages")
public class MessageController {
	
	private List<Message> mensajes = new ArrayList<>();
	
	public MessageController() {
		this.mensajes.add(new Message(1L, "Suscríbete"));
		this.mensajes.add(new Message(2L, "Vamos a darle"));
	}
	
	@GetMapping("/listar")
	public List<Message> listarMensajes(){
		return this.mensajes;
	}

}
