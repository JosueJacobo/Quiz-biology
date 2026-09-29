package com.example.data.questions

import com.example.data.models.Question

val defaultQuestionsPart1 = listOf(
    // Nivel 1: Anatomía Básica de la Hoja
    Question(
        levelNumber = 1,
        category = "Botánica",
        questionText = "¿Cuál es el principal tejido fotosintético que se encuentra en el interior de una hoja?",
        optionA = "Mesófilo",
        optionB = "Epidermis",
        optionC = "Cutícula",
        optionD = "Cámbium",
        correctOptionIndex = 0,
        explanation = "El mesófilo, especialmente el parénquima en empalizada, concentra la mayor cantidad de cloroplastos para la fotosíntesis."
    ),
    // Nivel 2: Raíces y Sistemas Radiculares
    Question(
        levelNumber = 2,
        category = "Botánica",
        questionText = "¿Qué estructura protege el ápice de la raíz durante su crecimiento a través del suelo?",
        optionA = "Zona pilífera",
        optionB = "Cofia o caliptra",
        optionC = "Endodermis",
        optionD = "Corteza",
        correctOptionIndex = 1,
        explanation = "La cofia o caliptra es una cubierta protectora que resguarda el meristemo apical de la raíz del desgaste mecánico."
    ),
    // Nivel 3: El Tallo Vegetal y Conducción
    Question(
        levelNumber = 3,
        category = "Botánica",
        questionText = "¿Cuál es la función principal de los nudos en los tallos de las plantas?",
        optionA = "Absorber agua del suelo",
        optionB = "Puntos donde nacen las hojas y yemas",
        optionC = "Secretar resinas protectoras",
        optionD = "Producir polen",
        correctOptionIndex = 1,
        explanation = "Los nudos son las zonas engrosadas del tallo donde se insertan las hojas y se originan las yemas axilares."
    ),
    // Nivel 4: Tejidos Vasculares: Xilema y Floema
    Question(
        levelNumber = 4,
        category = "Botánica",
        questionText = "¿Qué tejido vegetal transporta la savia elaborada (azúcares) desde las hojas al resto de la planta?",
        optionA = "Xilema",
        optionB = "Floema",
        optionC = "Colénquima",
        optionD = "Esclerénquima",
        correctOptionIndex = 1,
        explanation = "El floema transporta la savia elaborada rica en sacarosa mediante sus tubos cribosos y células acompañantes."
    ),
    // Nivel 5: La Flor y Órganos Reproductores
    Question(
        levelNumber = 5,
        category = "Botánica",
        questionText = "¿Cómo se llama el conjunto de estambres que forma el aparato reproductor masculino de la flor?",
        optionA = "Gineceo",
        optionB = "Androceo",
        optionC = "Cáliz",
        optionD = "Corola",
        correctOptionIndex = 1,
        explanation = "El androceo es el órgano reproductor masculino de la flor, compuesto por filamento y antera donde se produce el polen."
    ),
    // Nivel 6: Estomas y Transpiración
    Question(
        levelNumber = 6,
        category = "Botánica",
        questionText = "¿Qué células regulan la apertura y cierre de los estomas en el envés de la hoja?",
        optionA = "Células oclusivas o de guarda",
        optionB = "Tricomas",
        optionC = "Células del súber",
        optionD = "Traqueidas",
        correctOptionIndex = 0,
        explanation = "Las células oclusivas cambian su turgencia para regular el intercambio gaseoso de CO2, O2 y vapor de agua."
    ),
    // Nivel 7: Clorofila y Cloroplastos
    Question(
        levelNumber = 7,
        category = "Botánica",
        questionText = "¿En qué compartimento del cloroplasto ocurre la captación de luz por los pigmentos de clorofila?",
        optionA = "Estroma",
        optionB = "Membrana del tilacoide",
        optionC = "Matriz mitocondrial",
        optionD = "Vacuola central",
        correctOptionIndex = 1,
        explanation = "La clorofila está incrustada en las membranas de los tilacoides, organizadas en pilas llamadas granas."
    ),
    // Nivel 8: La Fotosíntesis
    Question(
        levelNumber = 8,
        category = "Botánica",
        questionText = "¿Cuál es el gas que absorben las plantas durante la fase oscura (ciclo de Calvin) para sintetizar glucosa?",
        optionA = "Oxígeno (O2)",
        optionB = "Dióxido de carbono (CO2)",
        optionC = "Nitrógeno (N2)",
        optionD = "Metano (CH4)",
        correctOptionIndex = 1,
        explanation = "Las plantas fijan dióxido de carbono ambiental mediante la enzima RuBisCO para producir carbohidratos."
    ),
    // Nivel 9: Frutos y Semillas
    Question(
        levelNumber = 9,
        category = "Botánica",
        questionText = "¿A partir de qué estructura floral se desarrolla típicamente el fruto tras la fecundación?",
        optionA = "Del cáliz",
        optionB = "Del ovario maduro",
        optionC = "Del pétalo",
        optionD = "Del estigma",
        correctOptionIndex = 1,
        explanation = "El ovario de la flor se transforma y engrosa para dar origen al fruto, el cual protege las semillas."
    ),
    // Nivel 10: Gimnospermas
    Question(
        levelNumber = 10,
        category = "Botánica",
        questionText = "¿Cuál es una característica distintiva de las gimnospermas como los pinos y abetos?",
        optionA = "Producen flores vistosas con pétalos",
        optionB = "Poseen semillas desnudas, no encerradas en un fruto",
        optionC = "No poseen vasos conductores",
        optionD = "Crecen exclusivamente sumergidas en agua",
        correctOptionIndex = 1,
        explanation = "Gimnosperma significa 'semilla desnuda'; sus semillas se forman expuestas sobre escamas de conos o piñas."
    ),
    // Nivel 11: Angiospermas
    Question(
        levelNumber = 11,
        category = "Botánica",
        questionText = "¿Qué diferencia principal distingue a las monocotiledóneas de las dicotiledóneas en sus hojas?",
        optionA = "Las monocotiledóneas tienen nervaduras paralelas",
        optionB = "Las monocotiledóneas carecen de clorofila",
        optionC = "Las dicotiledóneas nunca florecen",
        optionD = "Las monocotiledóneas tienen raíces leñosas profundas",
        correctOptionIndex = 0,
        explanation = "Monocotiledóneas como los pastos y orquídeas presentan nerviación paralela, mientras dicotiledóneas tienen nerviación reticulada."
    ),
    // Nivel 12: Briófitas
    Question(
        levelNumber = 12,
        category = "Botánica",
        questionText = "¿Por qué los musgos (briófitas) tienen un tamaño pequeño y requieren ambientes húmedos?",
        optionA = "Carecen de tejido vascular verdadero (xilema y floema)",
        optionB = "No realizan fotosíntesis",
        optionC = "No tienen ADN en sus células",
        optionD = "Poseen un exoesqueleto rígido",
        correctOptionIndex = 0,
        explanation = "Al no poseer xilema ni floema verdaderos, el agua se transporta por difusión célula a célula, limitando su porte."
    ),
    // Nivel 13: Pteridófitas
    Question(
        levelNumber = 13,
        category = "Botánica",
        questionText = "¿Cómo se llaman las estructuras que agrupan esporangios en el envés de los frondes de los helechos?",
        optionA = "Estomas",
        optionB = "Soros",
        optionC = "Pistilos",
        optionD = "Lenticelas",
        correctOptionIndex = 1,
        explanation = "Los soros son cúmulos visibles de color pardo o dorado en el envés de las hojas de los helechos donde maduran las esporas."
    ),
    // Nivel 14: Fitohormonas
    Question(
        levelNumber = 14,
        category = "Botánica",
        questionText = "¿Qué hormona vegetal en forma de gas promueve la maduración acelerada de los frutos?",
        optionA = "Auxina",
        optionB = "Etileno",
        optionC = "Ácido abscísico",
        optionD = "Citoquinina",
        correctOptionIndex = 1,
        explanation = "El etileno es una hormona gaseosa que estimula la respiración climatérica y maduración de frutos como el plátano o tomate."
    ),
    // Nivel 15: Polinización Biótica
    Question(
        levelNumber = 15,
        category = "Botánica",
        questionText = "¿Cómo se denomina técnicamente a la polinización mediada por abejas e insectos?",
        optionA = "Anemofilia",
        optionB = "Entomofilia",
        optionC = "Hidrofilia",
        optionD = "Ornitofilia",
        correctOptionIndex = 1,
        explanation = "La entomofilia es la polinización biótica realizada por insectos atraídos por néctar, polen, aromas y colores florales."
    ),
    // Nivel 16: Plantas Carnívoras
    Question(
        levelNumber = 16,
        category = "Botánica",
        questionText = "¿Qué nutriente esencial obtienen principalmente las plantas carnívoras de sus presas?",
        optionA = "Nitrógeno y fósforo de suelos pobres",
        optionB = "Glucosa y carbohidratos directos",
        optionC = "Agua destilada pura",
        optionD = "Dióxido de carbono para respirar",
        correctOptionIndex = 0,
        explanation = "Viven en turberas con suelos muy ácidos y pobres en nitrógeno disponible, el cual extraen de las proteínas de los insectos."
    ),
    // Nivel 17: Plantas Suculentas y Metabolismo CAM
    Question(
        levelNumber = 17,
        category = "Botánica",
        questionText = "¿En qué momento abren sus estomas las plantas con metabolismo ácido de las crasuláceas (CAM)?",
        optionA = "Durante la noche para evitar pérdida de agua",
        optionB = "Al mediodía con máxima radiación",
        optionC = "Únicamente cuando está lloviendo",
        optionD = "Permanecen abiertos continuamente",
        correctOptionIndex = 0,
        explanation = "Fijan el CO2 de noche almacenándolo como malato, cerrando estomas de día bajo el calor extremo del desierto."
    ),
    // Nivel 18: Pared Celular Vegetal
    Question(
        levelNumber = 18,
        category = "Botánica",
        questionText = "¿Cuál es el polisacárido estructural más abundante en la pared celular de las plantas terrestres?",
        optionA = "Glucógeno",
        optionB = "Celulosa",
        optionC = "Quitina",
        optionD = "Almidón",
        correctOptionIndex = 1,
        explanation = "La celulosa forma microfibrillas que otorgan gran rigidez y resistencia mecánica a la célula vegetal."
    ),
    // Nivel 19: Germinación de Semillas
    Question(
        levelNumber = 19,
        category = "Botánica",
        questionText = "¿Cuál es la primera estructura del embrión vegetal que emerge durante la germinación de la semilla?",
        optionA = "La radícula (raíz primaria)",
        optionB = "El primer par de flores",
        optionC = "El peciolo",
        optionD = "El cambium suberoso",
        correctOptionIndex = 0,
        explanation = "La radícula emerge primero para anclar la plántula e iniciar la absorción vital de agua y minerales del sustrato."
    ),
    // Nivel 20: Árboles Gigantes y Dendrocronología
    Question(
        levelNumber = 20,
        category = "Botánica",
        questionText = "¿Qué tejido secundario genera los anillos anuales de crecimiento en el tronco de los árboles leñosos?",
        optionA = "Cámbium vascular",
        optionB = "Parénquima clorofílico",
        optionC = "Epidermis cerosa",
        optionD = "Tricomas glandulares",
        correctOptionIndex = 0,
        explanation = "El cámbium vascular produce xilema secundario hacia el interior y floema hacia el exterior; el xilema forma los anillos visibles."
    ),
    // Nivel 21: Poríferos
    Question(
        levelNumber = 21,
        category = "Zoología",
        questionText = "¿Qué células flageladas de las esponjas marinas generan corrientes de agua para capturar alimento?",
        optionA = "Coanocitos",
        optionB = "Pinacocitos",
        optionC = "Células musculares",
        optionD = "Nematocistos",
        correctOptionIndex = 0,
        explanation = "Los coanocitos poseen un flagelo central rodeado por un collar de microvellosidades que filtra partículas orgánicas y bacterias."
    ),
    // Nivel 22: Cnidarios
    Question(
        levelNumber = 22,
        category = "Zoología",
        questionText = "¿Qué orgánulo celular de las medusas y anémonas dispara un filamento con toxina al contacto?",
        optionA = "Nematocisto",
        optionB = "Cloroplasto",
        optionC = "Ribosoma",
        optionD = "Vacuola contráctil",
        correctOptionIndex = 0,
        explanation = "Los nematocistos contenidos en los cnidocitos se disparan por presión hidrostática inyectando líquido urticante para defensa o caza."
    ),
    // Nivel 23: Platelmintos
    Question(
        levelNumber = 23,
        category = "Zoología",
        questionText = "¿Qué característica morfológica corporal distingue a las planarias (gusanos planos)?",
        optionA = "Son celomados con patas articuladas",
        optionB = "Son acelomados con simetría bilateral y cuerpo aplanado dorsoventralmente",
        optionC = "Poseen concha calcárea externa",
        optionD = "Tienen esqueleto óseo interno",
        correctOptionIndex = 1,
        explanation = "Carecen de cavidad celómica general y tienen cuerpos extremadamente delgados que permiten la respiración por difusión cutánea."
    ),
    // Nivel 24: Nemátodos
    Question(
        levelNumber = 24,
        category = "Zoología",
        questionText = "¿Cómo es la sección transversal del cuerpo de un nemátodo?",
        optionA = "Cilíndrica no segmentada",
        optionB = "Aplanada como cinta",
        optionC = "Dividida en anillos conspicuos",
        optionD = "Con forma pentagonal",
        correctOptionIndex = 0,
        explanation = "Los nemátodos son gusanos redondos cilíndricos con cutícula flexible de colágeno y tubo digestivo completo con boca y ano."
    ),
    // Nivel 25: Anélidos
    Question(
        levelNumber = 25,
        category = "Zoología",
        questionText = "¿Qué estructura engrosada y glandular de la lombriz de tierra interviene en la reproducción formando un capullo?",
        optionA = "Clitelo",
        optionB = "Manto",
        optionC = "Rádula",
        optionD = "Tórax",
        correctOptionIndex = 0,
        explanation = "El clitelo secreta un moco protector que forma el capullo donde se depositan los óvulos y el esperma para su desarrollo."
    )
)
