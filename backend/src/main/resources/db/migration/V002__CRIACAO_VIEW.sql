CREATE VIEW projeto.vw_leilao_dto AS
SELECT ROW_NUMBER() OVER () AS id,
       e.id AS comprador,
       l.inicio_previsto AS inicio_previsto,
       (lo.valor_inicial * lo.quantidade) AS total_leilao
FROM projeto.leilao AS l
         INNER JOIN projeto.comprador c ON c.leilao = l.id
         INNER JOIN projeto.empresa e ON e.id = c.empresa
         INNER JOIN projeto.lote lo ON lo.leilao = l.id;