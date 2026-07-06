# Cognitive Distortion detection

## TODO: Rerun notebooks

## Objective

The project aims to develop a system that can automatically detect cognitive distortions in conversations between patients and therapists. Cognitive distortions are irrational or biased ways of thinking that can contribute to negative emotions and behaviors.

## About Dataset

The dataset, utilized in the research titled "Detecting Cognitive Distortions from Patient-Therapist Interactions" by Sagarika Shreevastava and Peter W. Foltz, contains patient-therapist interaction data and annotated cognitive distortions. It comprises two main files: Therapist_responses.csv and Annotated_data.csv 

Therapist_responses.csv
    Content: Contains questions posed by patients and responses from licensed therapists.
    Key Column: ID_number, which matches the questions and responses to the annotations in the second file.

Annotated_data.csv

    Content: Contains 2530 annotated samples of patient inputs, detailing cognitive distortions.
    Columns:
        ID_number: Links to corresponding therapist responses in Therapist_responses.csv.
        Patient Question: The actual questions posted by patients.
        Distorted part: Sentences indicating cognitive distortions, if any.
        Dominant Distortion: The primary cognitive distortion detected in the patient's input.
        Secondary distortion (Optional): Additional distortion detected if the annotators couldn't decide the dominant one.

Types of Cognitive Distortions Annotated

    All-or-nothing thinking: Viewing situations in black-and-white terms.
        Example: "If I am not a complete success at my job; then I am a total failure."

    Overgeneralization: Drawing broad conclusions from limited information.
        Example: "One nurse was rude to me, so all medical staff must be rude."

    Mental filter: Focusing solely on the negatives and ignoring positives.
        Example: Ignoring all positive feedback and only remembering a single criticism.

    Should statements: Imposing strict rules on behavior.
        Example: "I should pick up after myself more."

    Labeling: Reducing oneself or others to a single negative descriptor.
        Example: "I am a failure."

    Personalization: Taking blame for events outside one's control.
        Example: "It's my fault that the project failed."

    Magnification: Exaggerating the importance of problems.
        Example: "If I don't pass this test, I will never be successful."

    Emotional Reasoning: Assuming feelings reflect reality.
        Example: "I feel like a failure, so I must be one."

    Mind Reading: Presuming to know what others think.
        Example: "They won't understand me."

    Fortune-telling: Predicting negative outcomes without evidence.
        Example: "I know the interview will go badly."
