package com.example.data.questions

import com.example.data.models.Question

val defaultQuestionsPart2 = listOf(
    // Nivel 26: Moluscos Gasterópodos
    Question(
        levelNumber = 26,
        category = "Zoología",
        questionText = "¿Qué estructura raspadora de quitina utilizan los caracoles para alimentarse de vegetales o algas?",
        optionA = "Rádula",
        optionB = "Pico córneo",
        optionC = "Mandíbula dentada",
        optionD = "Branquia",
        correctOptionIndex = 0,
        explanation = "La rádula es una cinta flexible con filas transversales de dientes córneos quitinosos que raspa el alimento como una lija."
    ),
    // Nivel 27: Moluscos Bivalvos
    Question(
        levelNumber = 27,
        category = "Zoología",
        questionText = "¿Cómo capturan su alimento la mayoría de los bivalvos (almejas, mejillones)?",
        optionA = "Cazando presas activas con tentáculos",
        optionB = "Filtrando partículas en suspensión mediante cilios en sus branquias",
        optionC = "Parasitando peces marinos",
        optionD = "Triturando rocas ricas en minerales",
        correctOptionIndex = 1,
        explanation = "Los bivalvos hacen circular agua por sifones y usan sus branquias ciliadas cubiertas de moco para atrapar microplancton."
    ),
    // Nivel 28: Cefalópodos
    Question(
        levelNumber = 28,
        category = "Zoología",
        questionText = "¿Qué células elásticas con sacos de pigmento permiten a los pulpos y calamares cambiar de color instantáneamente?",
        optionA = "Cromatóforos",
        optionB = "Melanocitos fijos",
        optionC = "Queratinocitos",
        optionD = "Osteocitos",
        correctOptionIndex = 0,
        explanation = "Los cromatóforos se expanden o contraen mediante diminutos músculos controlados directamente por el sistema nervioso central."
    ),
    // Nivel 29: Artrópodos: Exoesqueleto
    Question(
        levelNumber = 29,
        category = "Zoología",
        questionText = "¿De qué polisacárido nitrogenado resistente está compuesto principalmente el exoesqueleto de los artrópodos?",
        optionA = "Quitina",
        optionB = "Colágeno",
        optionC = "Glucógeno",
        optionD = "Fibrina",
        correctOptionIndex = 0,
        explanation = "La quitina, a menudo asociada con proteínas y carbonato de calcio en crustáceos, confiere protección rígida pero ligera."
    ),
    // Nivel 30: Anatomía de los Insectos
    Question(
        levelNumber = 30,
        category = "Zoología",
        questionText = "¿Cuántas patas articuladas posee un insecto adulto en su región torácica?",
        optionA = "4 patas",
        optionB = "6 patas (3 pares)",
        optionC = "8 patas",
        optionD = "10 patas",
        correctOptionIndex = 1,
        explanation = "La clase Insecta o Hexapoda se define por poseer 3 pares de patas locomotoras insertadas exclusivamente en el tórax."
    ),
    // Nivel 31: Metamorfosis de Insectos
    Question(
        levelNumber = 31,
        category = "Zoología",
        questionText = "¿Cuál es la secuencia de etapas en una metamorfosis completa (holometábola) como la de mariposas y escarabajos?",
        optionA = "Huevo -> Ninfa -> Adulto",
        optionB = "Huevo -> Larva -> Pupa (crisálida) -> Adulto (imago)",
        optionC = "Adulto -> Larva -> Huevo",
        optionD = "Huevo -> Capullo -> Ninfa",
        correctOptionIndex = 1,
        explanation = "Los insectos holometábolos pasan por cuatro fases bien diferenciadas morfológica y fisiológicamente."
    ),
    // Nivel 32: Arácnidos
    Question(
        levelNumber = 32,
        category = "Zoología",
        questionText = "¿Qué apéndices bucales articulados usan las arañas para sujetar presas e inyectar veneno?",
        optionA = "Antenas sensoriales",
        optionB = "Quelíceros",
        optionC = "Mandíbulas masticadoras",
        optionD = "Pinzas quelipedas",
        correctOptionIndex = 1,
        explanation = "Las arañas carecen de mandíbulas y antenas; sus primeros apéndices son quelíceros provistos de colmillos conectados a glándulas venenosas."
    ),
    // Nivel 33: Crustáceos
    Question(
        levelNumber = 33,
        category = "Zoología",
        questionText = "¿Cuántos pares de antenas poseen típicamente los crustáceos (cangrejos, camarones)?",
        optionA = "Ninguno",
        optionB = "1 par",
        optionC = "2 pares (anténulas y antenas)",
        optionD = "4 pares",
        correctOptionIndex = 2,
        explanation = "A diferencia de insectos y miriápodos que tienen un solo par, los crustáceos son los únicos artrópodos con dos pares de antenas."
    ),
    // Nivel 34: Miriápodos
    Question(
        levelNumber = 34,
        category = "Zoología",
        questionText = "¿Qué diferencia locomotor y alimenticia existe entre los ciempiés (quilópodos) y los milpiés (diplópodos)?",
        optionA = "Los ciempiés son carnívoros con un par de patas por segmento; los milpiés son herbívoros/detritívoros con dos pares",
        optionB = "Los milpiés vuelan y los ciempiés nadan",
        optionC = "Los ciempiés carecen de patas verdaderas",
        optionD = "Ambos tienen exactamente 100 patas fijas",
        correctOptionIndex = 0,
        explanation = "Los ciempiés poseen forcípulas venenosas y 1 par de patas por metámero, mientras los milpiés tienen 2 pares por segmento corporal fusionado."
    ),
    // Nivel 35: Equinodermos
    Question(
        levelNumber = 35,
        category = "Zoología",
        questionText = "¿Qué sistema hidráulico interno usan las estrellas de mar para la locomoción y captura de presas?",
        optionA = "Sistema circulatorio cerrado con hemoglobina",
        optionB = "Sistema vascular acuífero o ambulacral",
        optionC = "Tubos de Malpighi",
        optionD = "Linfáticos valvulares",
        correctOptionIndex = 1,
        explanation = "El sistema ambulacral canaliza agua de mar hacia cientos de pies ambulacrales que actúan como ventosas hidráulicas."
    ),
    // Nivel 36: Simetría en Equinodermos
    Question(
        levelNumber = 36,
        category = "Zoología",
        questionText = "¿Qué simetría corporal presentan las estrellas y erizos de mar adultos?",
        optionA = "Simetría pentarradial (en 5 partes)",
        optionB = "Asimetría total",
        optionC = "Simetría bilateral estricta",
        optionD = "Simetría esférica continua",
        correctOptionIndex = 0,
        explanation = "Los equinodermos adultos se caracterizan por una simetría radial pentámera alrededor de un eje oral-aboral central."
    ),
    // Nivel 37: Ojos Compuestos
    Question(
        levelNumber = 37,
        category = "Zoología",
        questionText = "¿Cómo se llaman las unidades ópticas individuales que componen el ojo compuesto de un insecto o crustáceo?",
        optionA = "Omatidios",
        optionB = "Conos y bastones",
        optionC = "Fóveas",
        optionD = "Cálices visuales",
        correctOptionIndex = 0,
        explanation = "Cada omatidio tiene su propia lente corneal, cono cristalino y fotorreceptores, captando un mosaico panorámico de alta velocidad."
    ),
    // Nivel 38: Comunicación Química
    Question(
        levelNumber = 38,
        category = "Zoología",
        questionText = "¿Cómo se denominan las sustancias químicas volátiles que los animales secretan para comunicarse con individuos de su especie?",
        optionA = "Feromonas",
        optionB = "Enzimas digestivas",
        optionC = "Anticuerpos",
        optionD = "Hormonas tiroideas",
        correctOptionIndex = 0,
        explanation = "Las feromonas transmiten mensajes precisos como alarma, rastros de comida, marcaje territorial o cortejo reproductor."
    ),
    // Nivel 39: Insectos Sociales
    Question(
        levelNumber = 39,
        category = "Zoología",
        questionText = "¿Cómo comunican las abejas melíferas obreras la distancia y dirección precisa de una fuente floral a sus compañeras?",
        optionA = "Mediante la 'danza del meneo' o danza en ocho dentro de la colmena",
        optionB = "Emitiendo ultrasonidos agudos",
        optionC = "Golpeando sus alas en el suelo de la colmena",
        optionD = "Produciendo destellos luminosos",
        correctOptionIndex = 0,
        explanation = "La danza waggle codifica el ángulo de la flor respecto al sol y el tiempo de meneo señala la distancia al objetivo."
    ),
    // Nivel 40: Bioluminiscencia Animal
    Question(
        levelNumber = 40,
        category = "Zoología",
        questionText = "¿Qué enzima oxida el sustrato luciferina en presencia de ATP para generar luz fría en las luciérnagas?",
        optionA = "Luciferasa",
        optionB = "Amilasa",
        optionC = "Pepsina",
        optionD = "Polimerasa",
        correctOptionIndex = 0,
        explanation = "La luciferasa cataliza la reacción luminiscente con casi 100% de eficiencia energética, sin disipar calor nocivo."
    ),
    // Nivel 41: Peces Condrictios
    Question(
        levelNumber = 41,
        category = "Zoología",
        questionText = "¿De qué tejido está constituido el esqueleto de tiburones, mantarrayas y quimeras?",
        optionA = "Cartílago flexible, sin tejido óseo verdadero",
        optionB = "Hueso denso mineralizado",
        optionC = "Quitina pura",
        optionD = "Concha espiculada",
        correctOptionIndex = 0,
        explanation = "La clase Chondrichthyes posee un endoesqueleto cartilaginoso liviano que les brinda notable hidrodinámica y flexibilidad."
    ),
    // Nivel 42: Peces Osteictios
    Question(
        levelNumber = 42,
        category = "Zoología",
        questionText = "¿Qué órgano hidrostático lleno de gas permite a los peces óseos flotar a distintas profundidades sin gastar energía?",
        optionA = "Vejiga natatoria",
        optionB = "Opérculo branquial",
        optionC = "Línea lateral",
        optionD = "Hígado graso único",
        correctOptionIndex = 0,
        explanation = "Al regular el volumen de gas en la vejiga natatoria, el pez ajusta su densidad para mantener flotabilidad neutra."
    ),
    // Nivel 43: La Línea Lateral en Peces
    Question(
        levelNumber = 43,
        category = "Zoología",
        questionText = "¿Qué detecta el sistema sensorial de la línea lateral en los peces?",
        optionA = "Vibraciones mecánicas, corrientes y cambios de presión en el agua circundante",
        optionB = "Variaciones de color en el fondo marino",
        optionC = "El sabor salino del agua",
        optionD = "La temperatura corporal del cardumen",
        correctOptionIndex = 0,
        explanation = "Está compuesto por neuromastos con células ciliadas que perciben mínimos movimientos de agua para nadar en cardumen y esquivar obstáculos."
    ),
    // Nivel 44: Anfibios Anuros
    Question(
        levelNumber = 44,
        category = "Zoología",
        questionText = "¿Cuál de estas características distingue a los anuros (ranas y sapos) adultos de otros anfibios?",
        optionA = "Ausencia de cola en el estado adulto",
        optionB = "Presencia de escamas córneas en el dorso",
        optionC = "Respiración exclusivamente branquial permanente",
        optionD = "Patas anteriores más largas que las posteriores",
        correctOptionIndex = 0,
        explanation = "El orden Anura (sin cola) pierde la cola durante la metamorfosis y desarrolla potentes extremidades traseras adaptadas al salto."
    ),
    // Nivel 45: Anfibios Urodelos
    Question(
        levelNumber = 45,
        category = "Zoología",
        questionText = "¿Qué fenómeno biológico presenta el ajolote mexicano (Ambystoma mexicanum) al conservar branquias y forma larvaria en estado adulto reproductor?",
        optionA = "Neotenia",
        optionB = "Ecdisis",
        optionC = "Diapausa",
        optionD = "Bioluminiscencia",
        correctOptionIndex = 0,
        explanation = "La neotenia es la retención de características anatómicas larvarias juveniles en organismos adultos sexualmente maduros."
    ),
    // Nivel 46: Respiración Cutánea en Anfibios
    Question(
        levelNumber = 46,
        category = "Zoología",
        questionText = "¿Qué condición física es obligatoria para que los anfibios puedan realizar el intercambio de oxígeno a través de su piel?",
        optionA = "Mantener la piel húmeda y vascularizada mediante secreciones mucosas",
        optionB = "Exponerse a temperaturas bajo cero",
        optionC = "Desarrollar una gruesa capa de queratina impermeable",
        optionD = "Permanecer inmóviles en la arena seca",
        correctOptionIndex = 0,
        explanation = "Los gases solo pueden difundirse a través de los capilares cutáneos si la superficie de la piel está continuamente humedecida."
    ),
    // Nivel 47: Reptiles Escamosos
    Question(
        levelNumber = 47,
        category = "Zoología",
        questionText = "¿De qué proteína fibrosa e impermeable están compuestas las escamas epidérmicas de los reptiles?",
        optionA = "Queratina",
        optionB = "Colágeno",
        optionC = "Miosina",
        optionD = "Hemocianina",
        correctOptionIndex = 0,
        explanation = "La queratina beta y alfa en sus escamas previene la deshidratación y protege su cuerpo contra la abrasión mecánica en tierra seca."
    ),
    // Nivel 48: Quelonios
    Question(
        levelNumber = 48,
        category = "Zoología",
        questionText = "¿A qué elementos del esqueleto interno están fusionados los escudos óseos del caparazón de las tortugas?",
        optionA = "A las costillas y vértebras dorsales",
        optionB = "A los huesos del cráneo",
        optionC = "A las falanges distales únicamente",
        optionD = "A los cartílagos del oído medio",
        correctOptionIndex = 0,
        explanation = "El caparazón óseo de la tortuga es una modificación estructural integrada con las costillas expandidas y la columna vertebral dorsal."
    ),
    // Nivel 49: Crocodilios
    Question(
        levelNumber = 49,
        category = "Zoología",
        questionText = "¿Cuántas cavidades completas posee el corazón de los cocodrilos y caimanes?",
        optionA = "2 cavidades",
        optionB = "3 cavidades",
        optionC = "4 cavidades (2 aurículas y 2 ventrículos separados con foramen de Panizza)",
        optionD = "1 cavidad tubular",
        correctOptionIndex = 2,
        explanation = "A diferencia de otros reptiles con 3 cámaras, los cocodrilos tienen un tabique ventricular completo de 4 cámaras anatómicas."
    ),
    // Nivel 50: Termorregulación Animal
    Question(
        levelNumber = 50,
        category = "Fisiología",
        questionText = "¿Qué término define a los animales cuya temperatura corporal depende primordialmente de fuentes externas de calor ambiental?",
        optionA = "Ectotermos (comúnmente 'de sangre fría')",
        optionB = "Endotermos homeotermos",
        optionC = "Hipotermos estables",
        optionD = "Isotérmicos constantes",
        correctOptionIndex = 0,
        explanation = "Los ectotermos (como lagartos y ranas) regulan su calor mediante pautas de comportamiento, asoleándose en rocas o buscando sombras."
    )
)
