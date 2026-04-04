package com.kaiser.financ.jobs;

import com.kaiser.financ.entities.DespesaEntity;
import com.kaiser.financ.entities.NotificacaoEntity;
import com.kaiser.financ.entities.UsuarioEntity;
import com.kaiser.financ.repositories.DespesaRepository;
import com.kaiser.financ.repositories.NotificacaoRepository;
import com.kaiser.financ.services.EmailService;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class NotificacaoJob {

  private static final Logger LOG = LoggerFactory.getLogger(NotificacaoJob.class);

  @Autowired
  private DespesaRepository despesaRepository;

  @Autowired
  private NotificacaoRepository notificacaoRepository;

  @Autowired
  private EmailService emailService;

  @Value("${notificacao.dias-antecedencia:3}")
  private int diasAntecedencia;

  @Scheduled(cron = "0 0 8 * * *")
  public void verificarDespesasPendentes() {
    LOG.info("Iniciando job de notificação de despesas...");

    LocalDate limite = LocalDate.now().plusDays(diasAntecedencia);

    List<DespesaEntity> despesas = despesaRepository.findByPagoFalseAndDtVencimentoLessThanEqual(limite);

    if (despesas.isEmpty()) {
      LOG.info("Nenhuma despesa pendente encontrada até {} dias à frente.", diasAntecedencia);
      return;
    }

    Map<UsuarioEntity, List<DespesaEntity>> porUsuario =
        despesas.stream().collect(Collectors.groupingBy(DespesaEntity::getUsuario));

    porUsuario.forEach((usuario, despesasUsuario) -> {
      LOG.info("Processando {} despesa(s) para o usuário: {}", despesasUsuario.size(), usuario.getEmail());

      String descricao = String.format(
          "Você tem %d despesa(s) pendente(s) de pagamento. Verifique seu e-mail.",
          despesasUsuario.size());

      NotificacaoEntity notificacao = new NotificacaoEntity(null, descricao, new Date(), null, usuario, false);
      notificacaoRepository.save(notificacao);

      emailService.sendNotificacaoDespesasEmail(usuario, despesasUsuario);
    });

    LOG.info("Job de notificação finalizado. {} usuário(s) notificado(s).", porUsuario.size());
  }
}
